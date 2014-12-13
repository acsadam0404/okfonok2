package hu.okfonok.user

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement
import hu.okfonok.common.Address
import hu.okfonok.common.Settlement

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
	private Address address

	Address getAddress() {
		if (!address) {
			address = new Address()
		}
		address
	}

	Date lastLogin

	void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin
		firstLogin = false
	}

	Date registrationDate

	/** readonly kívülről, setLastLogin állítja */
	@NotNull
	private boolean firstLogin = true

	boolean isFirstLogin(){
		firstLogin
	}


	@OneToMany
	private Set<Advertisement> savedAds

	@Embedded
	private Profile profile

	Profile getProfile() {
		if (!profile) {
			profile = new Profile()
		}
		profile
	}


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

	/**
	 * elmenti a usert és elküldi a confirmation emailt
	 */
	User register() {
		username = profile.email
		registrationDate = new Date()
		save()
		print 'sending confirmation mail' //TODO
	}
}
