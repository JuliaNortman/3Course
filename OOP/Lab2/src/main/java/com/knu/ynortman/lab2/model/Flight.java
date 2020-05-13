package com.knu.ynortman.lab2.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flight {
	private int id;
	private City departCity;
	private LocalDateTime departTime;
	private City destCity;
	private LocalDateTime destTime;
	
	@JsonIgnoreProperties("flightes")
	private List<CrewMember> crewMembers;
	
	public void setCrewMembers(List<CrewMember> crewMembers) {
		if(crewMembers != null) {
			crewMembers.forEach(member->member.addFlight(this));
		}
	}
}
