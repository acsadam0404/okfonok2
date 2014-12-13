package hu.okfonok.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SkillRepo extends JpaRepository<Skill, Long> {
	Skill findByName(String name);
}
