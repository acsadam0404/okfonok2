package hu.okfonok.common;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepo extends JpaRepository<Settlement, Long>{
	//TODO findAll-ra cache
	
	Settlement findByZipcode(int zipcode);
}
