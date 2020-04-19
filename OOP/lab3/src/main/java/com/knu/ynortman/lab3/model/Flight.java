package com.knu.ynortman.lab3.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Future;

import lombok.Data;

@Data
@Entity
public class Flight {
	@Id
	@GeneratedValue(generator = "flight_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "flight_id_seq", sequenceName = "flight_id_seq", allocationSize = 5)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "departure_city_id")
	private City departCity;
	
	@Future(message = "Departure time cannot refer to the past")
	private LocalDateTime departTime;
	
	@ManyToOne
	@JoinColumn(name = "dest_city_id")
	private City destCity;
	
	@Future(message = "Arrival time cannot refer to the past")
	private LocalDateTime destTime;
}
