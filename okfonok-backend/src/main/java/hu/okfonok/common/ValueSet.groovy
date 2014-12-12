package hu.okfonok.common

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull


@Entity
@Table(name = 'valueset')
@EqualsAndHashCode(includes = ['name'])
class ValueSet extends BaseEntity {
	private static ValueSetRepo repo

	ValueSet() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ValueSetRepo)
		}
	}

	@NotNull
	String name

	@NotNull
	@OneToMany(mappedBy = 'valueSet', fetch = FetchType.EAGER)
	List<ValueSetEntry> entries

	@Override
	String toString() {
		return "ValueSet($name)"
	}

	static List<ValueSetEntry> getEntries(String name) {
		repo.findByName(name).entries
	}
}
