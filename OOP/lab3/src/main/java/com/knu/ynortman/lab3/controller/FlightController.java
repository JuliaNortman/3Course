package com.knu.ynortman.lab3.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knu.ynortman.lab3.model.CrewMember;
import com.knu.ynortman.lab3.model.Flight;
import com.knu.ynortman.lab3.service.FlightService;

@RestController
@RequestMapping(path = "flight", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<Flight>> getAllFlights() {
		try {
			Iterable<Flight> flights = flightService.getAllFlights();
			if(IterableUtils.size(flights) > 0) {
				return new ResponseEntity<Iterable<Flight>>(flightService.getAllFlights(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<Flight>>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Iterable<Flight>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Flight> getFlightById(@PathVariable("id") Integer id) {
		try {
			Optional<Flight> flight = flightService.getFlight(id);
			if(flight.isPresent()) {
				return new ResponseEntity<Flight>(flight.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/admin/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> postFlight(@RequestBody @Valid Flight flight) {
		try {
			return new ResponseEntity<Flight>(flightService.createFlight(flight), HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/dispatcher/{id}/addmember", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> addFlightMember(@RequestBody CrewMember crewMember,
			@PathVariable("id") int flightId) {
		try {
			return new ResponseEntity<Flight>(flightService.addMember(flightId, crewMember), HttpStatus.CREATED);
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/dispatcher/{id}/deletemember", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> deleteFlightMember(@RequestBody CrewMember crewMember,
			@PathVariable("id") int flightId) {
		try {
			return new ResponseEntity<Flight>(flightService.deleteMember(flightId, crewMember), HttpStatus.OK);
		}
		catch (IllegalArgumentException e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/admin/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> updateFlight(@RequestBody @Valid Flight flight) {
		try {
			return new ResponseEntity<Flight>(flightService.update(flight), HttpStatus.OK);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<Flight>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<Flight>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/admin/delete/{id}")
	public ResponseEntity<Flight> deleteFlight(@PathVariable("id") Integer id) {
		flightService.deleteById(id);
		return new ResponseEntity<Flight>(HttpStatus.OK);
	}
}
