package hu.okfonok.common

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name= "settlement")
@EqualsAndHashCode
class Settlement extends BaseEntity {
	Integer zip

	String settlement
}
