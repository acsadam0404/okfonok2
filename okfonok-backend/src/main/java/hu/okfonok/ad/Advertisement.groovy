package hu.okfonok.ad;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.common.Address
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = 'advertisement')
@EqualsAndHashCode
class Advertisement extends BaseEntity{
	private static AdvertisementRepo repo

	Advertisement() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(AdvertisementRepo)
		}
	}

	public static final String USER = "user"
	public static final String DESCRIPTION = "description"
	public static final String REMUNERATION = "remuneration"
	public static final String CATEGORY = "category"
	public static final String ADDRESS = "address"

	@ManyToOne
	@NotNull
	User user

	@Size(max = 2000)
	@NotNull
	String description

	@NotNull
	String remuneration

	@ManyToOne
	@NotNull
	JobCategory category

	@Embedded
	Address address = new Address()

	static List<Advertisement> findByUsername(String username) {
		repo.findByUser(User.get(username))
	}

	static List<Advertisement> findAll() {
		repo.findAll()
	}

	Advertisement save() {
		repo.save(this)
	}
}
