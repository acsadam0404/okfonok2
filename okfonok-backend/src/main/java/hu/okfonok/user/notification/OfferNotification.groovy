package hu.okfonok.user.notification

import hu.okfonok.offer.Offer
import hu.okfonok.user.ServiceLocator

import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "offernotification")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
class OfferNotification extends Notification {
	@ManyToOne
	Offer offer

	private static OfferNotificationRepo notificationRepo

	protected OfferNotificationRepo getRepo() {
		if (ServiceLocator.loaded && !notificationRepo)  {
			notificationRepo = ServiceLocator.getBean(OfferNotificationRepo)
		}
		notificationRepo
	}

	OfferNotification() {
	}
}
