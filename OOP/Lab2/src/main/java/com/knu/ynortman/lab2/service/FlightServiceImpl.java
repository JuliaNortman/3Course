package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.dao.FlightDao;
import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;


public class FlightServiceImpl implements FlightService {

	@Override
	public Flight createFlight(Flight flight) throws ServerException {
		return FlightDao.insertFlight(flight);
	}

	@Override
	public List<Flight> getAllFlights() throws ServerException {
		List<Flight> flights = FlightDao.getAllFlights();
		return flights;
	}

	@Override
	public Flight getFlight(int id) throws ServerException {
		return FlightDao.getFlightById(id);
	}

	@Override
	public Flight update(Flight flight) throws ServerException {
		return FlightDao.updateFlight(flight);
	}

	@Override
	public Flight addMember(int flight, CrewMember crewMember) throws ServerException {
		return FlightDao.addCrewMember(flight, crewMember);
	}

	@Override
	public Flight deleteMember(int flight, CrewMember crewMember) throws ServerException {
		return FlightDao.deleteFlightMember(flight, crewMember);
	}

	@Override
	public void deleteById(int id) throws ServerException {
		FlightDao.deleteFlight(id);
	}

	

}