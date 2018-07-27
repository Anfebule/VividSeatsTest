package com.vividSeats.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity for Database object Acquaintance
 * @author andres.buitrago
 *
 */
@Entity
public class Acquaintance{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private Long personId;
	private Long acquaintanceId;

    protected Acquaintance() {}

    public Acquaintance(Long personId, Long acquaintanceId) {
    	this.personId = personId;
    	this.acquaintanceId = acquaintanceId;
    }

    public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getAcquaintanceId() {
		return acquaintanceId;
	}

	public void setAcquaintanceId(Long acquaintanceId) {
		this.acquaintanceId = acquaintanceId;
	}

	@Override
    public String toString() {
        return String.format(
                "Acquaintance[personId='%s', acquaintanceId='%s']",
                personId, acquaintanceId);
    }
	
}
