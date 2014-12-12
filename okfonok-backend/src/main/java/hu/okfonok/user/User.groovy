package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.Address

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
@EqualsAndHashCode(includes = ["username"])
class User extends BaseEntity{
	private static UserRepo userRepo

	private static UserRepo getRepo() {
		if (ServiceLocator.loaded && !userRepo)  {
			userRepo = ServiceLocator.getBean(UserRepo)
		}
		userRepo
	}

	@NotNull
	String username
	@NotNull
	String password
	@Embedded
	Address address
	
	static User get(String username) {
		repo.findByUsername(username)
	}

	User save() {
		repo.save(this)
	}

	@Override
	String toString() {
		username
	}
	
	
	/* TODO nem unsave az angol neve az biztos */
	void unsaveAdvertisement(Advertisement ad) {
		//TODO
	}
	
	void saveAdvertisement(Advertisement ad) {
		//TODO
	}

	boolean isAdvertisementSaved(Advertisement ad) {
		//TODO
		return false
	}
}
