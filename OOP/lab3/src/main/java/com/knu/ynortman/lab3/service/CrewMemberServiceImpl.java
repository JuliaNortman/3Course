package com.knu.ynortman.lab3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knu.ynortman.lab3.model.CrewMember;
import com.knu.ynortman.lab3.model.Flight;
import com.knu.ynortman.lab3.repository.CrewMemberRepository;
import com.knu.ynortman.lab3.repository.FlightRepository;

@Service
public class CrewMemberServiceImpl implements CrewMemberService{

	@Autowired
	private CrewMemberRepository crewRepo;
	@Autowired
	private FlightRepository flightRepo;
	
	@Override
	public CrewMember createMember(CrewMember crewMember) {
		return crewRepo.save(crewMember);
	}

	@Override
	public Iterable<CrewMember> getAllMembers() {
		return crewRepo.findAll();
	}

	@Override
	public Optional<CrewMember> getMember(int id) {
		return crewRepo.findById(id);
	}

	@Override
	public Iterable<CrewMember> getAllFlightCrew(int flightId) {
		return crewRepo.findByFlightId(flightId);
	}

	@Override
	public Iterable<Flight> getAllFlightsForMember(int id) {
		return flightRepo.findByCrewId(id);
	}

	@Override
	public CrewMember update(CrewMember member) {
		return this.createMember(member);
	}

	@Override
	public void delete(CrewMember crewMember) {
		crewRepo.delete(crewMember);
	}

	@Override
	public void deleteById(int id) {
		crewRepo.deleteById(id);
	}

}
