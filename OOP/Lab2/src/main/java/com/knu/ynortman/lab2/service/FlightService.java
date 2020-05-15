package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public interface FlightService {

	public Flight createFlight(Flight flight) throws ServerException;
	public List<Flight> getAllFlights() throws ServerException;
	public Flight getFlight(int id) throws ServerException;
	public Flight update(Flight flight) throws ServerException;
	public Flight addMember(int flight, CrewMember crewMember) throws ServerException;
	public Flight deleteMember(int flight, CrewMember crewMember) throws ServerException;
	public void deleteById(int id) throws ServerException;
}
