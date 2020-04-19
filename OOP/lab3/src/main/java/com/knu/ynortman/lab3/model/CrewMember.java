package com.knu.ynortman.lab3.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class CrewMember {
	@Id
	@GeneratedValue(generator = "member_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "member_id_seq", sequenceName = "member_id_seq", allocationSize = 5)
	private int id;
	
	@NotBlank(message = "Person name is mandatory")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private CrewRole role;
	
	@ManyToMany(mappedBy = "crewMembers")
	private Set<Flight> flightes;
}
