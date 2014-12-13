package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.transaction.annotation.Transactional

@Entity
@Table(name = "rating")
@EqualsAndHashCode
class Rating extends BaseEntity{
	private static RatingRepo ratingRepo

	private static RatingRepo getRepo() {
		if (ServiceLocator.loaded && !ratingRepo)  {
			ratingRepo = ServiceLocator.getBean(RatingRepo)
		}
		ratingRepo
	}

	@ManyToOne
	@NotNull
	Skill skill

	@ManyToOne
	@NotNull
	User raterUser

	@ManyToOne
	User ratedUser

	@NotNull
	Double value

	@Override
	String toString() {
		"$ratedUser's $skill skill rated by $raterUser: $value"
	}

	@Transactional
	Rating save() {
		Rating rating = repo.save(this)
		ratedUser.getRatings().add(rating)
		ratedUser.save()
		rating
	}

	@Transactional(readOnly = true)
	static List<Skill> findSkillsNotRated(User user) {
		def allSkills = Skill.findAll()
		def userSkills = user.ratings.collect() { it.skill }
		def notRatedSkills = allSkills - userSkills
		notRatedSkills
	}
}
