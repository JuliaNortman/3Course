package com.knu.ynortman.lab3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knu.ynortman.lab3.model.CrewMember;
import com.knu.ynortman.lab3.model.Flight;
import com.knu.ynortman.lab3.repository.CityRepository;
import com.knu.ynortman.lab3.repository.CrewMemberRepository;
import com.knu.ynortman.lab3.repository.FlightRepository;


@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private CrewMemberRepository crewRepo;
	
	@Override
	public Flight createFlight(Flight flight) {
		if(!cityRepo.existsById(flight.getDepartCity().getId())) {
			throw new IllegalArgumentException("Departure city with id " + flight.getDepartCity().getId() + " does not exist");
		}
		if(!cityRepo.existsById(flight.getDestCity().getId())) {
			throw new IllegalArgumentException("Destination city with id " + flight.getDestCity().getId()+ " does not exist");
		}
		flight.getCrewMembers().forEach((member)->{
			if(!crewRepo.existsById(member.getId())){
				throw new IllegalArgumentException("Crew member with id " + member.getId() + " does not exist");
			}
		});
		return flightRepo.save(flight);
	}

	@Override
	public Iterable<Flight> getAllFlights() {
		return flightRepo.findAll();
	}

	@Override
	public Optional<Flight> getFlight(int id) {
		return flightRepo.findById(id);
	}

	@Override
	public Flight update(Flight flight) {
		//TODO remove crew from existing flight
		return this.createFlight(flight);
	}

	@Override
	public void delete(Flight flight) {
		flightRepo.delete(flight);

	}

	@Override
	public void deleteById(int id) {
		flightRepo.deleteById(id);
	}

	@Override
	public Flight addMember(int flightId, CrewMember crewMember) {
		if(!flightRepo.existsById(flightId)) {
			throw new IllegalArgumentException("Flight with id " + flightId + " does not exist");
		}
		try {
			flightRepo.addCrewMember(flightId, crewMember.getId());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return flightRepo.findById(flightId).get();
	}

	@Override
	public Flight deleteMember(int flightId, CrewMember crewMember) {
		if(!flightRepo.existsById(flightId)) {
			throw new IllegalArgumentException("Flight with id " + flightId + " does not exist");
		}
		flightRepo.deleteCrewMember(flightId, crewMember.getId());
		return flightRepo.findById(flightId).get();
	}

}
