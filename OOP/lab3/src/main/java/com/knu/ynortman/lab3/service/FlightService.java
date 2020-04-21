package com.knu.ynortman.lab3.service;

import java.util.Optional;

import com.knu.ynortman.lab3.model.Flight;

public interface FlightService {

	public Flight createFlight(Flight flight);
	public Iterable<Flight> getAllFlights();
	public Optional<Flight> getFlight(int id);
	public Flight update(Flight flight);
	public void delete(Flight flight);
	public void deleteById(int id);
}
