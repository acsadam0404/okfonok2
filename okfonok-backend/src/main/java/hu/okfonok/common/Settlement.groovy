package hu.okfonok.common

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name= "settlement")
class Settlement extends BaseEntity {
	private static SettlementRepo settlementRepo

	private static SettlementRepo getRepo() {
		if (ServiceLocator.loaded && !settlementRepo)  {
			settlementRepo = ServiceLocator.getBean(SettlementRepo)
		}
		settlementRepo
	}

	/**
	 * csak jpa miatt
	 */
	protected Settlement() {
	}

	Settlement(int zipcode, String settlement) {
		this.zipcode = zipcode
		this.settlement = settlement
	}

	private int zipcode

	private String settlement

	int getZipcode() {
		zipcode
	}

	String getSettlement() {
		settlement
	}

	@Override
	String toString() {
		zipcode + " - " + settlement
	}

	static Settlement findByZipcode(int zipcode) {
		repo.findByZipcode(zipcode)
	}

	static List<Settlement> findAll() {
		repo.findAll()
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + zipcode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.is(obj))
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settlement other = (Settlement) obj;
		if (zipcode != other.zipcode)
			return false;
		return true;
	}
}
