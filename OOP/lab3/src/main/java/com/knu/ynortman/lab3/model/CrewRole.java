package com.knu.ynortman.lab3.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class CrewRole {
	@Id
	@GeneratedValue(generator = "crewrole_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "crewrole_id_seq", sequenceName = "crewrole_id_seq", allocationSize = 5)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private CrewRoleEnum role;
}
