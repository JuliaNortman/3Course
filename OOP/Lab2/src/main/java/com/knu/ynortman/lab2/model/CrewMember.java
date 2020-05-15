package com.knu.ynortman.lab2.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CrewMember {
	private int id;
	private String name;
	private CrewRole role;
	
	@EqualsAndHashCode.Exclude
	@JsonIgnoreProperties("crewMembers")
	@JsonInclude(Include.NON_NULL)
	private List<Flight> flightes;
	
	public void addFlight(Flight flight) {
		if(flightes == null) {
			flightes = new LinkedList<Flight>();
		}
		flightes.add(flight);
	}
}
