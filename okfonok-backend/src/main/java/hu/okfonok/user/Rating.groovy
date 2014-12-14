package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min
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

	Rating() {
		
	}
	
	Rating(Skill skill, User raterUser, User ratedUser, Double value) {
		this.value = value;
		this.ratedUser = ratedUser;
		this.raterUser = raterUser;
		this.skill = skill;
	}
	
	@ManyToOne
	@NotNull
	Skill skill

	@ManyToOne
	@NotNull
	User raterUser

	@ManyToOne
	User ratedUser

	boolean isOwnRating() {
		return raterUser == ratedUser	
	}
	
	@NotNull
	@Min(1L)
	@Max(10L)
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
