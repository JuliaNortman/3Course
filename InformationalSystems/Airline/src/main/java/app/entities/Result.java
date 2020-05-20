package app.entities;

import lombok.Data;

@Data
public class Result {
	private String direction;
	private String departureTime;
	private String busySeatsPercentage;
	private String ticketsNum;
	private String ticketTotalCost;
	private String busySeatsNum;
	private String popularity;
	private String baggageNum;
	private String baggageTotalCost;
	private String priorityNum;
	private String priorityTotalCost;
}
