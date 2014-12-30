package hu.okfonok.ad;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.Config
import hu.okfonok.common.Address
import hu.okfonok.common.DateInterval
import hu.okfonok.offer.Offer
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import java.math.RoundingMode
import java.nio.file.Path
import java.nio.file.Paths

import javax.persistence.ElementCollection
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
	public static final String ADDRESS = "address"	public static final String MAINCATEGORY = "mainCategory"
	public static final String AVERAGEPRICE = "averagePrice"
	public static final String OFFERSNUMBER = "offersNumber"
	public static final String MESSAGECOUNT = "messageCount"

	/**
	 * Létrehoz egy hirdetést és beállítja az uuid-ját. 
	 * Az uuid beállítása már a létrehozás pillanatában megtörténik, a még nem mentett entitásnál is létezik.
	 */
	Advertisement() {
		uuid = UUID.randomUUID().toString()
	}

	@ManyToOne
	@NotNull
	User user

	@Size(max = 2000)
	@NotNull
	String description

	@NotNull
	String remuneration

	@NotNull
	private String uuid

	String getUuid() {
		uuid
	}

	@ManyToOne
	@NotNull
	JobCategory category

	@NotNull
	Date dateCreated

	@Embedded
	Address address = new Address()

	boolean homeJob

	@Min(1L)
	@NotNull
	Integer maxOffer

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "advertisement")
	Set<Offer> offers

	@ElementCollection(fetch = FetchType.EAGER)
	Set<DateInterval> preferredIntervals = [] as Set

	JobCategory getMainCategory() {
		category.mainCategory
	}

	Advertisement save() {
		dateCreated = new Date()
		repo.save(this)
	}

	BigDecimal getAveragePrice() {
		BigDecimal sum = BigDecimal.ZERO
		if (offers) {
			offers.each { sum += it.amount }
			/* 0 precision, mert most csak forint van és nem érdekelnek a törtek  */
			/* rounding szükséges mert végtelen törteknél ArithhmeticException jönne */
			sum = sum.divide(new BigDecimal(offers.size()), 0, RoundingMode.HALF_UP)
		}
		sum
	}

	Offer getAcceptedOffer() {
		offers.find { it.accepted }
	}

	boolean hasAcceptedOffer() {
		acceptedOffer != null
	}

	Collection<Offer> getRejectedOffers() {
		offers.findAll { !it.accepted }
	}

	int getOffersNumber() {
		offers.size()
	}

	public void share() {
		println "shared on facebook"
	}

	static List<Offer> findAcceptedOffers(User user, Date startDate, Date endDate) {
		def ads = findByUser(user).findAll{ it.acceptedOffer }
		ads.collect { it.acceptedOffer }
		//TODO startDate, endDate -től függjön
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

	static List<Advertisement> findByUserExcluded(User user) {
		repo.findByUserNot(user)
	}

	/**
	 * adott felhasználó adott-e már ajánlatot a hirdetésre
	 * @param user
	 * @return
	 */
	boolean hasOfferByUser(User user) {
		getOfferByUser(user) != null
	}

	/**
	 * TODO Optional?
	 * @param user
	 * @return null ha nincs ilyen offer
	 */
	Offer getOfferByUser(User user) {
		offers.find { it.user == user }
	}

	Path getImagePath() {
		Config.getAdRoot(user, uuid)
	}

	boolean hasImage() {
		getImagePath().toFile().listFiles().length > 0
	}
}
