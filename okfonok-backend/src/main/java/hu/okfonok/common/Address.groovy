package hu.okfonok.common

import javax.persistence.Embeddable
import javax.persistence.ManyToOne

import org.hibernate.annotations.Formula

@Embeddable
class Address implements Serializable {
	@ManyToOne
	Settlement settlement

	String other

	@Formula("0")
	private int dummyFieldForHibernateNotNullEmbedded

	@Override
	String toString() {
		settlement + " " + other
	}
}
