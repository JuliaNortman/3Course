package com.knu.ynortman.lab3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.lab3.model.CrewMember;

public interface CrewMemberRepository extends CrudRepository<CrewMember, Integer> {
	
	@Query(
			value = "SELECT * FROM crew_member INNER JOIN crew_flight ON crew_member.id=crew_flight.crew_id "
			+ "WHERE crew_flight.flight_id = cm.flightId",
			nativeQuery = true)
	Iterable<CrewMember> findByFlightId(int flightId);
}
