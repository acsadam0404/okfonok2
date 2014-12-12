package hu.okfonok.common;

import groovy.transform.EqualsAndHashCode
import groovy.transform.PackageScope
import hu.okfonok.BaseEntity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name =  "valuesetentry")
@EqualsAndHashCode
class ValueSetEntry extends BaseEntity{


	private ValueSetEntry() {
	}

	@NotNull
	String bkey

	@NotNull
	@ManyToOne
	ValueSet valueSet

	String getValue() {
		return bkey
	}

	@Override
	String toString() {
		value
	}
}
