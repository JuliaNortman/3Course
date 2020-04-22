package com.knu.ynortman.lab3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Country {
	@Id
	@GeneratedValue(generator = "country_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "country_id_seq", sequenceName = "country_id_seq", allocationSize = 5)
	@Column(updatable = false)
	private int id;
	
	@NotBlank(message = "Country name is mandatory")
	private String name;
}
