package com.knu.ynortman.lab2.service;

import java.util.Optional;

import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public interface FlightService {

	public Flight createFlight(Flight flight);
	public Iterable<Flight> getAllFlights();
	public Optional<Flight> getFlight(int id);
	public Flight update(Flight flight);
	public Flight addMember(int flight, CrewMember crewMember);
	public Flight deleteMember(int flight, CrewMember crewMember);
	public void delete(Flight flight);
	public void deleteById(int id);
}
