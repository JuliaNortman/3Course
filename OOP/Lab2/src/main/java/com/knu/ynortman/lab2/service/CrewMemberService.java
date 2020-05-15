package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public interface CrewMemberService {
	public CrewMember createMember(CrewMember crewMember) throws ServerException;
	public List<CrewMember> getAllMembers() throws ServerException;
	public CrewMember getMember(int id) throws ServerException;
	public List<CrewMember> getAllFlightCrew(int flightId) throws ServerException;
	public List<Flight> getAllFlightsForMember(int id) throws ServerException;
	public CrewMember update(CrewMember member) throws ServerException;
	public void deleteById(int id) throws ServerException;
}