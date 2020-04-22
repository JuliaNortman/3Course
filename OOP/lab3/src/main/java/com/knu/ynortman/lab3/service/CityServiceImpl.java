package com.knu.ynortman.lab3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knu.ynortman.lab3.model.City;
import com.knu.ynortman.lab3.repository.CityRepository;
import com.knu.ynortman.lab3.repository.CountryRepository;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private CountryRepository countryRepo;
	
	@Override
	public Iterable<City> getAllCities() {
		return cityRepo.findAll();
	}

	@Override
	public Iterable<City> getCountryCities(int countryId) {
		return cityRepo.findByCountryId(countryId);
	}

	@Override
	public Optional<City> getCity(int id) {
		return cityRepo.findById(id);
	}

	@Override
	public City create(City city) {
		if(countryRepo.existsById(city.getCountry().getId())) {
			return cityRepo.save(city);
		}
		throw new IllegalArgumentException("Country with id " + city.getCountry().getId() + " does not exist");
	}

	@Override
	public City update(City city) {
		return create(city);
	}

	@Override
	public void delete(City city) {
		cityRepo.delete(city);

	}

	@Override
	public void deleteById(int id) {
		cityRepo.deleteById(id);
	}

}
