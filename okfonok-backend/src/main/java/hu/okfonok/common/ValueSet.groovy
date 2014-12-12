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
	private static ValueSetRepo valueSetRepo
	
	private static ValueSetRepo getRepo() {
		if (ServiceLocator.loaded && !valueSetRepo)  {
			valueSetRepo = ServiceLocator.getBean(ValueSetRepo)
		}
		valueSetRepo
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
	
	List<String> getEntryValues() {
		entries.collect { it.value }
	}
	
	static ValueSet remuneration() {
		return repo.findByName("remuneration")
	}
	
}
