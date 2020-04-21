package com.knu.ynortman.lab3.repository;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.lab3.model.Flight;

public interface FlightRepository extends CrudRepository<Flight, Integer> {

}
