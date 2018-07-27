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
            if(celebs.size() > 1 || celebs.isEmpty()){
                answer = "There's no celb in the party";
            } else {
                Object[] celebsArray = celebs.toArray();
                Person person = personRepository.findById(Long.parseLong(celebsArray[0].toString())).get();
                answer = "The celeb is: "+person.getName();
            }
        }
        return answer;
    }

    /**
     * Compare acquaintances between people in the party and find the common one
     * @param peopleList
     * @return
     */
    public Set<Long> compareAcquaintances(ArrayList<Person> peopleList) {
        Person person1, person2;
        Set<Long> possibleCeleb = new HashSet<>();

        for (int i = 1; i < peopleList.size(); i++) {
            person2 = peopleList.get(i);
            ArrayList<Long> acquaintances2 = new ArrayList<>();
            
            for (Acquaintance acquaintance : acquaintanceRepository.findByPersonId(person2.getId())) {
            	acquaintances2.add(acquaintance.getAcquaintanceId());
    		}
           
            if (i == 1){
                person1 = peopleList.get(i - 1);
                ArrayList<Long> acquaintances1 = new ArrayList<>();
                for (Acquaintance acquaintance : acquaintanceRepository.findByPersonId(person1.getId())) {
                	acquaintances1.add(acquaintance.getAcquaintanceId());
        		}
             
                compareWithPreviousGuest(possibleCeleb, acquaintances1, acquaintances2);
            } else {
                compareWithPossibleCeleb(possibleCeleb, acquaintances2);
            }
        }

        return possibleCeleb;
    }

    /**
     * Compares acquaintances of one person between possible celebs list and removes from
     * possible celebs list the one who's not common
     * @param possibleCeleb
     * @param acquaintances2
     */
    public void compareWithPossibleCeleb(Set<Long> possibleCeleb, ArrayList<Long> acquaintances2) {
        Iterator<Long> possibleCelebItr = possibleCeleb.iterator();
        while (possibleCelebItr.hasNext() && !acquaintances2.isEmpty()){
            Long celebId = Long.parseLong(possibleCelebItr.next().toString());
            if (!acquaintances2.contains(celebId)){
                possibleCelebItr.remove();
            }
        }
    }

    /**
     * Compares acquaintances between two people and find the common one
     * @param possibleCeleb
     * @param acquaintances1
     * @param acquaintances2
     */
    public void compareWithPreviousGuest(Set<Long> possibleCeleb, ArrayList<Long> acquaintances1, ArrayList<Long> acquaintances2) {
        for (int j = 0; j<acquaintances2.size(); j++){
            if (acquaintances1.contains(acquaintances2.get(j))){
                possibleCeleb.add(acquaintances2.get(j));
            }
        }
    }
}
