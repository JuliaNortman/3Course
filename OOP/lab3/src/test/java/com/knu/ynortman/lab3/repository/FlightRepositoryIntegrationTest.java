package com.knu.ynortman.lab3.repository;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.knu.ynortman.lab3.model.City;
import com.knu.ynortman.lab3.model.Country;
import com.knu.ynortman.lab3.model.Flight;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightRepositoryIntegrationTest {
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private FlightRepository flightRepo;
	@Autowired
	private CrewMemberRepository crewRepo;
	
	@Test
	public void findByCrewIdTest() {
		Country ukraine = new Country("Ukraine");
		City kiev = new City("Kyiv", ukraine);
		City lviv = new City("Lviv", ukraine);
		Flight flight1 = new Flight(kiev, LocalDateTime.of(2021, 11, 11, 12, 0), lviv, LocalDateTime.of(2021, 11, 11, 15, 0));
		Flight flight2 = new Flight(kiev, LocalDateTime.of(2020, 11, 11, 12, 0), lviv, LocalDateTime.of(2021, 11, 11, 15, 0));
		entityManager.persist(flight1);
	    entityManager.flush();
	    entityManager.persist(flight2);
	    entityManager.flush();
	}
}
