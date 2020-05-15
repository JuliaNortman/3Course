package com.knu.ynortman.lab2.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class City {
	private int id;
	private String name;
	private Country country;
	
}
