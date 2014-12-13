package hu.okfonok.user

import java.nio.ReadOnlyBufferException;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement
import hu.okfonok.common.Address

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.transaction.annotation.Transactional;

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


	@OneToMany(fetch = FetchType.EAGER)
	private Set<Advertisement> savedAds

	@Embedded
	private Profile profile

	@OneToMany(mappedBy = "ratedUser", fetch = FetchType.EAGER)
	List<Rating> ratings

	Profile getProfile() {
		if (!profile) {
			profile = new Profile()
		}
		profile
	}

	@Transactional(readOnly = true)
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


	@Transactional
	void unsaveAdvertisement(Advertisement ad) {
		savedAds.remove(ad)
		save()
	}

	@Transactional
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
	@Transactional
	User register() {
		username = profile.email
		registrationDate = new Date()
		save()
		print 'sending confirmation mail' //TODO
	}
}
