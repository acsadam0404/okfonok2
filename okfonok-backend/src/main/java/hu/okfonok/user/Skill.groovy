package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull


@Entity
@Table(name = "skill")
@EqualsAndHashCode(includes = ["name"])
class Skill extends BaseEntity{
	private static SkillRepo skillRepo

	private static SkillRepo getRepo() {
		if (ServiceLocator.loaded && !skillRepo)  {
			skillRepo = ServiceLocator.getBean(SkillRepo)
		}
		skillRepo
	}


	@NotNull
	String name

	@NotNull
	String question

	@Override
	String toString() {
		name
	}

	static Skill findByName(String name) {
		repo.findByName(name)
	}

	static List<Skill> findAll() {
		repo.findAll()
	}
}
