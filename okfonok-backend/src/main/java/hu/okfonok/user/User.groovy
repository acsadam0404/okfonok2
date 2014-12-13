package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement
import hu.okfonok.common.Address

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.OneToMany
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

	Date lastLogin

	Date registrationDate

	boolean firstLogin = true

	@OneToMany
	private Set<Advertisement> savedAds

	@Embedded
	Profile profile

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
		savedAds.remove(ad)
		save()
	}

	void saveAdvertisement(Advertisement ad) {
		savedAds.add(ad)
		save()
	}

	boolean isAdvertisementSaved(Advertisement ad) {
		savedAds.contains(ad)
	}

	// kell ide eventbus és logineventnél lastLogin-t beállítani
}
