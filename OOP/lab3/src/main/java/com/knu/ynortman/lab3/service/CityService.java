package com.knu.ynortman.lab3.service;

import java.util.Optional;

import com.knu.ynortman.lab3.model.City;

public interface CityService {
	public Iterable<City> getAllCities();
	public Iterable<City> getCountryCities(int countryId);
	public Optional<City> getCity(int id);
	public City create(City city);
	public City update(City city);
	public void delete(City city);
	public void deleteById(int id);
}
