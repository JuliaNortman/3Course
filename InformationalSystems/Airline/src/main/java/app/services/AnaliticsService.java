package app.services;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import app.entities.Flight;
import app.entities.Result;
import app.entities.Ticket;

public class AnaliticsService {
	private final FlightService flightService;
	private final TicketService ticketService;
	private List<Flight> flights;
	private final int baggagePrice = 25;
	private final int priorityPrice = 18;
	
	public AnaliticsService() throws ClassNotFoundException, SQLException, ParseException {
		this.flightService = new FlightService();
		this.ticketService = new TicketService();
		this.flights = flightService.getAllFlights();
	}
	
	public List<Result> getAnalitics() throws ClassNotFoundException, SQLException, ParseException {
		this.flights = flightService.getAllFlights();
		/*StringBuffer res = new StringBuffer();
		
		res.append("Direction\t")
		.append("Departure Time\t")
		.append("Fill(%)\t")
		.append("Tickets Number(Total cost($))\t")
		.append("Busy seats Number\t")
		.append("Popularity\t")
		.append("Baggage quantity(Total baggage cost($))\t")
		.append("Priority quantity(Total priority cost($))\t")
		.append("\n\n");
		*/
		
		List<Result> result = new LinkedList<Result>();
		
		for(Flight flight : flights) {
			
			List<Ticket> tickets = ticketService.getTicketsByFlight(flight.getId());
			
			float busySeatsPercentage = (((float)flight.getSeatsNum()-(float)flight.getSeatsNumAvailable())/(float)flight.getSeatsNum())*100;
			
			
			String popularity = Float.compare(busySeatsPercentage, 50)>0 ? "popular" : "unpopular";
			int ticketsNum = tickets.size();
			int busySeatsNum = flight.getSeatsNum()-flight.getSeatsNumAvailable();
			int baggageNum= 0;
			int priorityNum = 0;
			float ticketTotalCost = 0;
			for(Ticket ticket : tickets) {
				if(ticket.hasBaggage()) ticketsNum++;
				if(ticket.hasPriority()) priorityNum++;
				ticketTotalCost += ticket.getPrice();
			}
			float baggageTotalCost = baggageNum*baggagePrice;
			float priorityTotalCost = priorityNum*priorityPrice;
			
			Result res = new Result();
			res.setDirection(flight.getDirection());
			res.setDepartureTime(flight.getDepartureTime());
			res.setBusySeatsPercentage(Float.toString(busySeatsPercentage));
			res.setTicketsNum(Integer.toString(ticketsNum));
			res.setTicketTotalCost(Float.toString(ticketTotalCost));
			res.setBusySeatsNum(Integer.toString(busySeatsNum));
			res.setPopularity(popularity);
			res.setBaggageNum(Integer.toString(baggageNum));
			res.setBaggageTotalCost(Float.toString(baggageTotalCost));
			res.setPriorityNum(Integer.toString(priorityNum));
			res.setPriorityTotalCost(Float.toString(priorityTotalCost));
			result.add(res);
			/*res.append(flight.getDirection())
			.append("\t")
			.append(flight.getDepartureTime())
			.append("\t")
			.append(busySeatsPercentage + "%")
			.append("\t")
			.append(ticketsNum + "(" + ticketTotalCost + "$)")
			.append("\t")
			.append(busySeatsNum)
			.append("\t")
			.append(popularity)
			.append("\t")
			.append(baggageNum + "(" + baggageTotalCost + "$)")
			.append("\t")
			.append(priorityNum + "(" + priorityTotalCost + "$)")
			.append("\n");*/
		}
		
		return result;
	}

}
