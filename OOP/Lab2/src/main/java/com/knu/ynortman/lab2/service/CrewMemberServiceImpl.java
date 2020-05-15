package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.dao.CrewMembersDao;
import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;

public class CrewMemberServiceImpl implements CrewMemberService{

	@Override
	public CrewMember createMember(CrewMember crewMember) throws ServerException {
		return CrewMembersDao.addCrewMember(crewMember);
	}

	@Override
	public List<CrewMember> getAllMembers() throws ServerException {
		return CrewMembersDao.getAllCrewMembers();
	}

	@Override
	public CrewMember getMember(int id) throws ServerException {
		return CrewMembersDao.getCrewMemberById(id);
	}

	@Override
	public List<CrewMember> getAllFlightCrew(int flightId) throws ServerException {
		return CrewMembersDao.getFlightMembers(flightId);
	}

	@Override
	public List<Flight> getAllFlightsForMember(int id) throws ServerException {
		return CrewMembersDao.getMemberFlights(id);
	}

	@Override
	public CrewMember update(CrewMember member) throws ServerException {
		return CrewMembersDao.updateCrewMember(member);
	}

	@Override
	public void deleteById(int id) throws ServerException {
		CrewMembersDao.deleteById(id);
	}

}