package app.servlets;

import app.entities.Flight;
import app.services.FlightService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@WebServlet(name = "FlightServlet", urlPatterns = "/flights")
public class FlightServlet extends HttpServlet {
    private FlightService fs = new FlightService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flightId = request.getParameter("flightId");
        if (flightId != null){
            showFlight(request, response);
        } else {
            listFlights(request, response);
        }
    }

    private void listFlights(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            List<Flight> flights = fs.getAllFlights();
            request.setAttribute("flights", flights);
            request.getRequestDispatcher("/views/index.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void showFlight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String flightIdStr = request.getParameter("flightId");
            int flightId = Integer.parseInt(flightIdStr);
            Flight flight = fs.getFlight(flightId);
            if (flight == null){
                request.setAttribute("message", "Виникла помилка");
                request.getRequestDispatcher("/views/message.jsp").forward(request, response);
            }else {
                request.setAttribute("flight", flight);
                request.getRequestDispatcher("/views/flight.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}
