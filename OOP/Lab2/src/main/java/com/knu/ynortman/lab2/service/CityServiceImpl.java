package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.dao.CityDao;
import com.knu.ynortman.lab2.model.City;

public class CityServiceImpl implements CityService {

	@Override
	public List<City> getAllCities() {
		return CityDao.getAllCities();
	}

}