package com.knu.ynortman.lab3.repository;

import org.springframework.data.repository.CrudRepository;

import com.knu.ynortman.lab3.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {

}
