package hu.okfonok.common

import javax.persistence.Embeddable
import javax.persistence.ManyToOne

import org.hibernate.annotations.Formula

@Embeddable
class Address implements Serializable {
	@ManyToOne
	Settlement settlement

	String other
	
	Address() {
		
	}
	
	Address(Settlement settlement, String other) {
		this.settlement = settlement
		this.other = other
	}

	void setZipcode(Integer zipcode) {
		if (zipcode) {
			settlement = Settlement.findByZipcode(zipcode)
		}
	}

	Integer getZipcode() {
		settlement?.zipcode
	}

	//TODO kell ez?
	@Formula("0")
	private int dummyFieldForHibernateNotNullEmbedded

	@Override
	String toString() {
		settlement.toString() + " " + other
	}
}
