package com.vividSeats.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vividSeats.domain.Acquaintance;

/**
 * Class in charge of CRUD or DAO functionality for Acquaintance table
 * @author andres.buitrago
 *
 */
public interface AcquaintanceRepository extends CrudRepository<Acquaintance, Long>{
	
	List<Acquaintance> findByPersonId(Long personId);
}
