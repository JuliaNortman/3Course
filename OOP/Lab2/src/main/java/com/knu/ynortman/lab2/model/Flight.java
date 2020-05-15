package com.knu.ynortman.lab2.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.knu.ynortman.lab2.util.LocalDateTimeDeserializer;
import com.knu.ynortman.lab2.util.LocalDateTimeSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flight {
	private int id;
	private City departCity;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime departTime;
	private City destCity;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime destTime;

	@JsonIgnoreProperties("flightes")
	private List<CrewMember> crewMembers;

	public void setCrewMembers(List<CrewMember> crewMembers) {
		this.crewMembers = crewMembers;
		if (crewMembers != null) {
			crewMembers.forEach(member -> member.addFlight(this));
		}
	}
}
