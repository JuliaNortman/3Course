package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public interface CrewMemberService {
	public CrewMember createMember(CrewMember crewMember);
	public List<CrewMember> getAllMembers();
	public CrewMember getMember(int id);
	public List<CrewMember> getAllFlightCrew(int flightId);
	public List<Flight> getAllFlightsForMember(int id);
	public CrewMember update(CrewMember member);
	public void delete(CrewMember crewMember);
	public void deleteById(int id);
}
