package com.knu.ynortman.lab2.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.CrewRole;
import com.knu.ynortman.lab2.model.CrewRoleEnum;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class CrewMembersDao {
	private static final Logger logger = LogManager.getRootLogger();
	
	private static final String crewIdByFlightIdQuery = 
			"SELECT crew_member.id, crew_member.name, crew_role.id, crew_role.role " + 
			"FROM crew_member INNER JOIN crew_role ON crew_member.role_id = crew_role.id " + 
			"WHERE crew_member.id IN ( " + 
			"						SELECT crew_flight.crew_id " + 
			"						FROM crew_flight " + 
			"						WHERE crew_flight.flight_id = ?)";
	private static final String crewMemberByIdQuery = 
			"SELECT crew_member.id, crew_member.name, crew_role.id, crew_role.role " + 
			"FROM crew_member INNER JOIN crew_role ON crew_member.role_id = crew_role.id";
	
	public static List<CrewMember> getFlightMembers(int flightId) {
		List<CrewMember> members = new LinkedList<CrewMember>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(crewIdByFlightIdQuery);
			ps.setInt(1, flightId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CrewMember member = crewMemberFromResultSet(rs);
				if(member != null) {
					members.add(member);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get members");
		}
		logger.info(members.size());
		return members;
	}
	
	public static CrewMember getCrewMemberById(int id) {
		CrewMember member = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(crewMemberByIdQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				member = crewMemberFromResultSet(rs);
			} else {
				logger.error("Cannot get crew memn=ber by id");
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get crew member by id");
			return null;
		}
		return member;
	}
	
	public static boolean iscrewMemberExists(int id) {
		return (getCrewMemberById(id) != null);
	}
	
	public static CrewMember crewMemberFromResultSet(ResultSet rs) throws SQLException {
		CrewMember member = new CrewMember();
		member.setId(rs.getInt(1));
		member.setName(rs.getString(2));
		CrewRole role = new CrewRole();
		role.setId(rs.getInt(3));
		role.setRole(CrewRoleEnum.valueOf(rs.getString(4)));
		member.setRole(role);
		
		return member;
	}
}
