package com.knu.ynortman.lab2.service;

import java.util.List;
import java.util.Optional;

import com.knu.ynortman.lab2.dao.FlightDao;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;


public class FlightServiceImpl implements FlightService {

	@Override
	public Flight createFlight(Flight flight) {
		return FlightDao.insertFlight(flight);
	}

	@Override
	public List<Flight> getAllFlights() {
		List<Flight> flights = FlightDao.getAllFlights();
		if(flights == null || flights.size() == 0) {
			return null;
		}
		return flights;
	}

	@Override
	public Flight getFlight(int id) {
		return FlightDao.getFlightById(id);
	}

	@Override
	public Flight update(Flight flight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flight addMember(int flight, CrewMember crewMember) {
		return FlightDao.addCrewMember(flight, crewMember);
	}

	@Override
	public Flight deleteMember(int flight, CrewMember crewMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Flight flight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	

}
