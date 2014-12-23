package hu.okfonok.offer

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity
import hu.okfonok.ad.Advertisement
import hu.okfonok.common.DateInterval
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.ElementCollection;
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.w3c.dom.events.UIEvent;

@Entity
@Table(name = "offer")
@EqualsAndHashCode(includes = ["amount", "advertisement", "user"])
class Offer extends BaseEntity{
	public static final String AMOUNT = "amount"
	public static final String USER = "user"
	public static final String ADVERTISEMENT = "advertisement"	public static final Object INTERVALS = "intervals";

	private static OfferRepo offerRepo

	private static OfferRepo getRepo() {
		if (ServiceLocator.loaded && !offerRepo)  {
			offerRepo = ServiceLocator.getBean(OfferRepo)
		}
		offerRepo
	}

	@NotNull
	BigDecimal amount

	@NotNull
	@ManyToOne
	Advertisement advertisement

	@ManyToOne
	@NotNull
	User user

	@ElementCollection(fetch = FetchType.EAGER)
	Set<DateInterval> intervals = [] as Set

	@NotNull
	boolean accepted

	public Offer() {
	}

	public Offer(User user, Advertisement advertisement, BigDecimal amount) {
		this.user = user
		this.advertisement = advertisement
		this.amount = amount
	}

	public void save() {
		advertisement.offers << this
		repo.save(this)
		advertisement.save()
	}
}
