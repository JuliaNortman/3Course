package com.knu.ynortman.lab3.service;

import java.util.Optional;

import com.knu.ynortman.lab3.model.CrewMember;
import com.knu.ynortman.lab3.model.Flight;


public interface CrewMemberService {
	public CrewMember createMember(CrewMember crewMember);
	public Iterable<CrewMember> getAllMembers();
	public Optional<CrewMember> getMember(int id);
	public Iterable<CrewMember> getAllFlightCrew(int flightId);
	public Iterable<Flight> getAllFlightsForMember(int id);
	public CrewMember update(CrewMember member);
	public void delete(CrewMember crewMember);
	public void deleteById(int id);
}
