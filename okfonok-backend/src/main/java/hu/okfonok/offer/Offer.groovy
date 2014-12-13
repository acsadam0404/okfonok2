package hu.okfonok.offer

import groovy.transform.EqualsAndHashCode
import hu.okfonok.BaseEntity;
import hu.okfonok.ad.Advertisement
import hu.okfonok.user.ServiceLocator
import hu.okfonok.user.User

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "offer")
@EqualsAndHashCode(includes = ["amount", "advertisement", "user"])
class Offer extends BaseEntity{
	public static final String AMOUNT = "amount"
	public static final String USER = "user"
	public static final String ADVERTISEMENT = "advertisement"

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
