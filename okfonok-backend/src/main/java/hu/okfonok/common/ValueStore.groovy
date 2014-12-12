package hu.okfonok.common

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "valuestore")
@EqualsAndHashCode
class ValueStore extends BaseEntity  {

	private static ValueStoreRepo valueStoreRepo

	private static ValueStoreRepo getRepo() {
		if (ServiceLocator.loaded && !valueStoreRepo)  {
			valueStoreRepo = ServiceLocator.getBean(ValueStoreRepo)
		}
		valueStoreRepo
	}

	@Column
	@NotNull
	String name

	@Column
	@NotNull
	String value
}
