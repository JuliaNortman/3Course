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
import com.knu.ynortman.lab3.service.CrewMemberServiceImpl;

@RestController
@RequestMapping(path = "crew", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CrewMemberController {
	@Autowired
	private CrewMemberServiceImpl crewService;

	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<CrewMember>> getAllCrewMembers() {
		try {
			Iterable<CrewMember> members = crewService.getAllMembers();
			if (IterableUtils.size(members) > 0) {
				return new ResponseEntity<Iterable<CrewMember>>(members, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<CrewMember>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Iterable<CrewMember>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Integer id) {
		Optional<CrewMember> member = crewService.getMember(id);
		try {
			if (member.isPresent()) {
				return new ResponseEntity<CrewMember>(member.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<CrewMember>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<CrewMember>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/flight/{id}")
	public ResponseEntity<Iterable<CrewMember>> getFlightCrew(@PathVariable("id") Integer flightId) {
		try {
			Iterable<CrewMember> members = crewService.getAllFlightCrew(flightId);
			if (IterableUtils.size(members) > 0) {
				return new ResponseEntity<Iterable<CrewMember>>(members, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<CrewMember>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Iterable<CrewMember>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/{id}/flights")
	public ResponseEntity<Iterable<Flight>> getCrewMemberFlights(@PathVariable("id") Integer memberId) {
		try {
			Iterable<Flight> flights = crewService.getAllFlightsForMember(memberId);
			if (IterableUtils.size(flights) > 0) {
				return new ResponseEntity<Iterable<Flight>>(flights, HttpStatus.OK);
			} else {
				return new ResponseEntity<Iterable<Flight>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Flight>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/dispatcher/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CrewMember> addCrewMember(@RequestBody @Valid CrewMember member) {
		return new ResponseEntity<CrewMember>(crewService.createMember(member), HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/dispatcher/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CrewMember> updateCrewMember(@Valid @RequestBody CrewMember member) {
		return new ResponseEntity<CrewMember>(crewService.update(member), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/dispatcher/delete/{id}")
	public ResponseEntity<CrewMember> delete(@PathVariable Integer id) {
		crewService.deleteById(id);
		return new ResponseEntity<CrewMember>(HttpStatus.OK);
	}
}
