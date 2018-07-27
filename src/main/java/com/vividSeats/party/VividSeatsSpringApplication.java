package com.vividSeats.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vividSeats.domain.Acquaintance;
import com.vividSeats.domain.Person;
import com.vividSeats.repository.AcquaintanceRepository;
import com.vividSeats.repository.PersonRepository;

/**
 * Principal class in charge of starting spring boot project
 * @author andres.buitrago
 *
 */
@SpringBootApplication
@EntityScan("com.vividSeats.domain")
@EnableJpaRepositories("com.vividSeats.repository")
@ComponentScan("com.vividSeats.controller")
public class VividSeatsSpringApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(VividSeatsSpringApplication.class, args);
	}
	
	/**
	 * Bean creation for uploading sample data to database
	 * @param personRepository
	 * @param acquaintanceRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(PersonRepository personRepository, AcquaintanceRepository acquaintanceRepository) {
		return (args) -> {
			// save a couple of people
			personRepository.save(new Person("Juan"));
			personRepository.save(new Person("Deiver"));
			personRepository.save(new Person("Juan Manuel"));
			personRepository.save(new Person("Mario"));
			personRepository.save(new Person("David"));
			personRepository.save(new Person("Gerson"));
			personRepository.save(new Person("Natalia"));
			
			acquaintanceRepository.save(new Acquaintance (1L, 4L));
			acquaintanceRepository.save(new Acquaintance (1L, 6L));
			acquaintanceRepository.save(new Acquaintance (2L, 6L));
			acquaintanceRepository.save(new Acquaintance (2L, 7L));
			acquaintanceRepository.save(new Acquaintance (2L, 5L));
			acquaintanceRepository.save(new Acquaintance (3L, 6L));
			acquaintanceRepository.save(new Acquaintance (3L, 7L));
			acquaintanceRepository.save(new Acquaintance (4L, 3L));
			acquaintanceRepository.save(new Acquaintance (4L, 6L));
			acquaintanceRepository.save(new Acquaintance (5L, 3L));
			acquaintanceRepository.save(new Acquaintance (5L, 6L));
			acquaintanceRepository.save(new Acquaintance (5L, 2L));
			acquaintanceRepository.save(new Acquaintance (7L, 6L));
		};
	}
}
