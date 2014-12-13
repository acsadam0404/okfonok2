package hu.okfonok.user

import groovy.transform.EqualsAndHashCode;

import javax.persistence.Embeddable


@Embeddable
//@EqualsAndHashCode //nem kell embeddablere
class Profile {
	String firstName

	String lastName

	String getName() {
		"$lastName $firstName"
	}

	@Override
	String toString() {
		name
	}
}
