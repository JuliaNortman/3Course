package com.knu.ynortman.lab3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class City {
	@Id
	@GeneratedValue(generator = "city_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "city_id_seq", sequenceName = "city_id_seq", allocationSize = 5)
	@Column(updatable = false)
	private int id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotNull(message = "Country field cannot be empty")
	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false, updatable = false)
	private Country country;
	
	public City(String name, Country country) {
		this.name = name;
		this.country = country;
	}
}
