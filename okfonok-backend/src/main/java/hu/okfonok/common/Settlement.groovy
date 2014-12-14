package hu.okfonok.common

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name= "settlement")
@EqualsAndHashCode
class Settlement extends BaseEntity {
	private static SettlementRepo settlementRepo

	private static SettlementRepo getRepo() {
		if (ServiceLocator.loaded && !settlementRepo)  {
			settlementRepo = ServiceLocator.getBean(SettlementRepo)
		}
		settlementRepo
	}
	
	Settlement() {
		
	}
	
	Settlement(int zipcode, String settlement) {
		this.zipcode = zipcode
		this.settlement = settlement
	}

	private Integer zipcode

	private String settlement

	Integer getZipcode() {
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
}
