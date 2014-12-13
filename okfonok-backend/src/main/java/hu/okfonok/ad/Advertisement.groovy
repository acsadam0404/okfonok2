package hu.okfonok.ad;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.common.Address
import hu.okfonok.offer.Offer
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = 'advertisement')
@EqualsAndHashCode(includes = ["user", "description", "dateCreated"])
class Advertisement extends BaseEntity{
	private static AdvertisementRepo advertisementRepo

	private static AdvertisementRepo getRepo() {
		if (ServiceLocator.loaded && !advertisementRepo)  {
			advertisementRepo = ServiceLocator.getBean(AdvertisementRepo)
		}
		advertisementRepo
	}

	public static final String USER = "user"
	public static final String DESCRIPTION = "description"
	public static final String REMUNERATION = "remuneration"
	public static final String CATEGORY = "category"
	public static final String ADDRESS = "address"	public static final String MAINCATEGORY = "mainCategory";

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

	@NotNull
	Date dateCreated

	@Embedded
	Address address = new Address()

	boolean homeJob

	@Min(1L)
	int maxOffer
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "advertisement")
	Set<Offer> offers
	
	JobCategory getMainCategory() {
		category.mainCategory
	}

	static List<Advertisement> findByUsername(String username) {
		findByUser(User.get(username))
	}
	
	static List<Advertisement> findByUser(User user) {
		repo.findByUser(user)
	}

	static List<Advertisement> findAll() {
		repo.findAll()
	}

	Advertisement save() {
		dateCreated = new Date()
		repo.save(this)
	}

	BigDecimal getAveragePrice() {
		new BigDecimal("5500")
	}

	public void share() {
		println "shared on facebook"
	}
}
