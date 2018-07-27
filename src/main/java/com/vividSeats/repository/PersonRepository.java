package com.vividSeats.repository;

import org.springframework.data.repository.CrudRepository;

import com.vividSeats.domain.Person;

/**
 * Class in charge of CRUD or DAO functionality for Person table
 * @author andres.buitrago
 *
 */
public interface PersonRepository extends CrudRepository<Person, Long>{
	
}
