package com.vividSeats.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vividSeats.domain.Person;
import com.vividSeats.repository.PersonRepository;

/**
 * Class in charge of exposing web services
 * @author andres.buitrago
 *
 */
@RestController
public class PartyRESTController {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	PartyController party;
	
	@RequestMapping("/")
    public String party() {
        
        ArrayList<Person> peopleList = new ArrayList<>();
           
        for (Person person : personRepository.findAll()) {
			peopleList.add(person);
		}
        
        return party.findCelebrity(peopleList);
    }
}
