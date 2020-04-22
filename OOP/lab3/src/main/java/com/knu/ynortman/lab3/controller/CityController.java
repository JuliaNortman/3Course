package com.knu.ynortman.lab3.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knu.ynortman.lab3.model.City;
import com.knu.ynortman.lab3.service.CityServiceImpl;

@RestController
@RequestMapping(path = "city", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class CityController {

	@Autowired
	private CityServiceImpl cityService;
	
	@GetMapping(path = "/all")
	public ResponseEntity<Iterable<City>> getAllCities() {
		Iterable<City> cities = cityService.getAllCities();
		if(IterableUtils.size(cities) > 0) {
			return new ResponseEntity<Iterable<City>>(cities, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<City>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<City> getCity(@PathVariable Integer id) {
		Optional<City> city = cityService.getCity(id);
		if(city.isPresent()) {
			return new ResponseEntity<City>(city.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/country/{id}")
	public ResponseEntity<Iterable<City>> getCountryCities(@PathVariable("id") Integer countryId) {
		Iterable<City> cities = cityService.getCountryCities(countryId);
		if(IterableUtils.size(cities) > 0) {
			return new ResponseEntity<Iterable<City>>(cities, HttpStatus.OK);
		} else {
			return new ResponseEntity<Iterable<City>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> createCity(@RequestBody @Valid City city) {
		try {
			return new ResponseEntity<City>(cityService.create(city), HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<City>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<City>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> updateCity(@RequestBody @Valid City city, @PathVariable Integer id) {
		try {
			return new ResponseEntity<City>(cityService.update(city), HttpStatus.OK);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<City>(HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<City>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<City> deleteCity(@PathVariable int id) {
		cityService.deleteById(id);
		return new ResponseEntity<City>(HttpStatus.OK);
	}
}
