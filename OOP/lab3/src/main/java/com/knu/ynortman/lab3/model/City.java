package com.knu.ynortman.lab3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class City {
	@Id
	@GeneratedValue(generator = "city_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "city_id_seq", sequenceName = "city_id_seq", allocationSize = 5)
	private int id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;
}
