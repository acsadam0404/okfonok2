package hu.okfonok.common

import javax.persistence.Embeddable
import javax.persistence.ManyToOne

import org.hibernate.annotations.Formula

@Embeddable
class Address implements Serializable {
	@ManyToOne
	Settlement settlement

	String other

	void setZipcode(Integer zipcode) {
		if (zipcode) {
			settlement = Settlement.findByZipcode(zipcode)
		}
	}

	Integer getZipcode() {
		settlement?.zipcode
	}

	@Formula("0")
	private int dummyFieldForHibernateNotNullEmbedded

	@Override
	String toString() {
		settlement + " " + other
	}
}
