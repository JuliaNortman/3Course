package com.knu.ynortman.lab3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.lab3.model.Flight;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
	@Query(
			value = "SELECT * FROM flight INNER JOIN crew_flight ON flight.id = crew_flight.flight_id"
					+ "WHERE crew_flight.crew_id = crewId",
			nativeQuery = true)
	Iterable<Flight> findByCrewId(int crewId);
}
