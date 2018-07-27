package com.vividSeats.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vividSeats.domain.Acquaintance;
import com.vividSeats.domain.Person;
import com.vividSeats.repository.AcquaintanceRepository;
import com.vividSeats.repository.PersonRepository;

/**
 * Class that contains business logic of party celeb finding
 * @author andres.buitrago
 *
 */
@Controller
public class PartyController {
	
	@Autowired
	AcquaintanceRepository acquaintanceRepository;
	@Autowired
	PersonRepository personRepository;
	
	public PartyController() {
	
	}

	/**
     * Finds a celeb between a list of people and their acquaintances
     * @param peopleList
     */
    public String findCelebrity(ArrayList<Person> peopleList){
        String answer;
        Set<Long> celebs = new HashSet<>();

        if (peopleList.isEmpty()){
            answer = "There's no people in the party, ergo, no celeb.";
        } else if (peopleList.size() == 1){
            answer = "There's only one person in the party, maybe he/she is the celeb?";
        } else {
            celebs = compareAcquaintances(peopleList);
            
            //We assume that if there's more than one person that doesn't know anybody
            //then there's no celeb, because the rule isn't complied
            if(celebs.size() > 1 || celebs.isEmpty() || hasCelebAcqauaintances(findPersonInCelebArray(celebs))){
                answer = "There's no celb in the party";
            } else {
                answer = "The celeb is: "+findPersonInCelebArray(celebs).getName();
            }
        }
        return answer;
    }
    
    /**
     * Checks if possible celebrity has acquaintances
     * @param person
     * @return
     */
    public boolean hasCelebAcqauaintances(Person person) {
    	boolean hasAqcuaintances;
    	if(acquaintanceRepository.findByPersonId(person.getId()).isEmpty()) {
    		hasAqcuaintances = false;
    	} else {
    		hasAqcuaintances = true;
    	}
    	return hasAqcuaintances;
    }

	/**
	 * Finds Person in celebs array first position
	 * @param celebs
	 * @return
	 */
	public Person findPersonInCelebArray(Set<Long> celebs) {
		Object[] celebsArray = celebs.toArray();
		Person person = personRepository.findById(Long.parseLong(celebsArray[0].toString())).get();
		return person;
	}

    /**
     * Compare acquaintances between people in the party and find the common one
     * @param peopleList
     * @return
     */
    public Set<Long> compareAcquaintances(ArrayList<Person> peopleList) {
        Person person1, person2;
        Set<Long> possibleCeleb = new HashSet<>();
        int emptyAcquaintanceCounter = 0;

        for (int i = 1; i < peopleList.size(); i++) {
            person2 = peopleList.get(i);
            ArrayList<Long> acquaintances2 = new ArrayList<>();
            
            for (Acquaintance acquaintance : acquaintanceRepository.findByPersonId(person2.getId())) {
            	acquaintances2.add(acquaintance.getAcquaintanceId());
    		}
           
            if (possibleCeleb.isEmpty()){
                person1 = peopleList.get(i - 1);
                ArrayList<Long> acquaintances1 = new ArrayList<>();
                for (Acquaintance acquaintance : acquaintanceRepository.findByPersonId(person1.getId())) {
                	acquaintances1.add(acquaintance.getAcquaintanceId());
        		}
             
                emptyAcquaintanceCounter = compareWithPreviousGuest(possibleCeleb, acquaintances1, acquaintances2, emptyAcquaintanceCounter);
            } else {
            	emptyAcquaintanceCounter = compareWithPossibleCeleb(possibleCeleb, acquaintances2, emptyAcquaintanceCounter);
            }
        }

        return possibleCeleb;
    }

    /**
     * Compares acquaintances of one person between possible celebs list and removes from
     * possible celebs list the one who's not common, returns amount of empty acquaintances
     * @param possibleCeleb
     * @param acquaintances2
     * @return emptyAcquaintanceCounter
     */
    public int compareWithPossibleCeleb(Set<Long> possibleCeleb, ArrayList<Long> acquaintances2, int emptyAcquaintanceCounter) {
        Iterator<Long> possibleCelebItr = possibleCeleb.iterator();
        if (acquaintances2.isEmpty()) {
        	emptyAcquaintanceCounter++;
        }
        	
        while (possibleCelebItr.hasNext() && emptyAcquaintanceCounter > 1){
            Long celebId = Long.parseLong(possibleCelebItr.next().toString());
            if (!acquaintances2.contains(celebId)){
                possibleCelebItr.remove();
            }
        }
        return emptyAcquaintanceCounter;
    }

    /**
     * Compares acquaintances between two people and find the common one, returns amount of empty acquaintances
     * @param possibleCeleb
     * @param acquaintances1
     * @param acquaintances2
     * @return emptyAcquaintanceCounter
     */
    public int compareWithPreviousGuest(Set<Long> possibleCeleb, ArrayList<Long> acquaintances1, 
    		ArrayList<Long> acquaintances2, int emptyAcquaintanceCounter) {
    	if (acquaintances1.isEmpty()) {
        	emptyAcquaintanceCounter++;
        }
    	if (acquaintances2.isEmpty()) {
    		emptyAcquaintanceCounter++;
    	}
        for (int j = 0; j<acquaintances2.size(); j++){
            if (acquaintances1.contains(acquaintances2.get(j))){
                possibleCeleb.add(acquaintances2.get(j));
            }
        }
        return emptyAcquaintanceCounter;
    }
}
