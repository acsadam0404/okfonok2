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

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "offer")
@EqualsAndHashCode(includes = ["amount", "advertisement", "user"])
@Transactional
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

	/**
	 * csak JPA-nak
	 */
	protected Offer() {
	}

	public Offer(User user, Advertisement advertisement, BigDecimal amount, Set<DateInterval> intervals) {
		this.user = user
		this.advertisement = advertisement
		this.amount = amount
		this.intervals = intervals
	}

	public void save() {
		repo.save(this)
		advertisement.offers << this
		advertisement.save()
	}

	/**
	 * Ha még nincs más elfogadott ajánlat, akkor ezt elfogadja
	 */
	void accept() {
		if (!advertisement.acceptedOffer) {
			accepted = true
			save()
			/* TODO publish AcceptOfferEvent */
		}
		else {
			throw new RuntimeException("Another offer is accepted. It shouldn't be possible to call this method.")
		}
	}

	/**
	 * Az accept ellentéte. Ha már elfogadott az ajánlat akkor visszavonja az elfogadást.
	 */
	void reject() {
		if (advertisement.acceptedOffer) {
			accepted = false
			save()
			/* TODO publish RejectOfferEvent */
		}
		else {
			throw new RuntimeException("A non-accepted offer is rejected. It shouldn't be possible to call this method.")
		}
	}

	/**
	 * csak nem elfogadott ajánlatot lehet törölni
	 * @return
	 */
	boolean delete() {
		if (!accepted) {
			advertisement.offers.remove(this)
			advertisement.save()
			repo.delete(id)
			/* TODO publish OfferDeletedEvent */
			return true
		}
		return false
	}
}
