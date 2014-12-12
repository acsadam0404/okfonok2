package hu.okfonok.common;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ValueSetRepo extends JpaRepository<ValueSet, Long> {
	ValueSet findByName(String name);
}
