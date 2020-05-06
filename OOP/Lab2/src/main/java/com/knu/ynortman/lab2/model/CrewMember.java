package com.knu.ynortman.lab2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class CrewMember {
	private int id;
	private String name;
	private CrewRole role;
	
	@JsonIgnoreProperties("crewMembers")
	private List<Flight> flightes;
}
