package com.knu.ynortman.lab3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.knu.ynortman.lab3.model.Flight;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
	@Query(value = "SELECT * FROM flight INNER JOIN crew_flight ON flight.id = crew_flight.flight_id"
					+ "WHERE crew_flight.crew_id = crewId",
			nativeQuery = true)
	Iterable<Flight> findByCrewId(int crewId);
	
	@Query(value = "INSERT INTO crew_flight(flight_id, crew_id) VALUES(:flight_id, :crew_id)", 
			nativeQuery = true)
	void addCrewMember(@Param("flight_id") Integer flightId, @Param("crew_id") Integer crewId);
	
	@Query(value = "DELETE FROM crew_flight WHERE flight_id = flightId AND crew_id = crewId", 
			nativeQuery = true)
	void deleteCrewMember(Integer flightId, Integer crewId);
}
