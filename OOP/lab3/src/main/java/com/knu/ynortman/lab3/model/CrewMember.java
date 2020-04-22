package com.knu.ynortman.lab3.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class CrewMember {
	@Id
	@GeneratedValue(generator = "member_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "member_id_seq", sequenceName = "member_id_seq", allocationSize = 5)
	@Column(updatable = false)
	private int id;
	
	@NotBlank(message = "Person name is mandatory")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private CrewRole role;
	
	@ManyToMany(mappedBy = "crewMembers")
	@JsonIgnoreProperties("crewmembers")
	//@JsonBackReference(value = "crew")
	private Set<Flight> flightes;
}
