package com.knu.ynortman.lab3.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Flight {
	@Id
	@GeneratedValue(generator = "flight_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "flight_id_seq", sequenceName = "flight_id_seq", allocationSize = 5)
	@Column(updatable = false)
	private int id;
	
	@NotNull(message = "Departure city cannot be null")
	@ManyToOne
	@JoinColumn(name = "departure_city_id", nullable = false)
	private City departCity;
	
	@Future(message = "Departure time cannot refer to the past")
	private LocalDateTime departTime;
	
	@NotNull(message = "Destination city cannot be null")
	@ManyToOne
	@JoinColumn(name = "dest_city_id", nullable = false)
	private City destCity;
	
	@Future(message = "Arrival time cannot refer to the past")
	private LocalDateTime destTime;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "crew_flight", 
        joinColumns = { @JoinColumn(name = "flight_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "crew_id") }
    )
	
	//@JsonManagedReference(value = "crew")
	@JsonIgnoreProperties("flightes")
	private Set<CrewMember> crewMembers;
}
