package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.dao.CrewMembersDao;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public class CrewMemberServiceImpl implements CrewMemberService{

	@Override
	public CrewMember createMember(CrewMember crewMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrewMember> getAllMembers() {
		return CrewMembersDao.getAllCrewMembers();
	}

	@Override
	public CrewMember getMember(int id) {
		return CrewMembersDao.getCrewMemberById(id);
	}

	@Override
	public Iterable<CrewMember> getAllFlightCrew(int flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Flight> getAllFlightsForMember(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrewMember update(CrewMember member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(CrewMember crewMember) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

}
