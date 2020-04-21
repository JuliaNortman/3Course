package com.knu.ynortman.lab3.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knu.ynortman.lab3.model.Flight;

@RestController
@RequestMapping(path = "flight",
				produces = "application/json")
@CrossOrigin(origins = "*")
public class FlightController {
	
	public Iterable<Flight> getAllFlights() {
		
	}
}
