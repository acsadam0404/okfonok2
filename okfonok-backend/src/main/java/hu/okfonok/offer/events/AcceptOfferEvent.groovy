package hu.okfonok.offer.events

import hu.okfonok.offer.Offer

class AcceptOfferEvent {
	private Offer acceptedOffer

	AcceptOfferEvent(Offer acceptedOffer) {
		this.acceptedOffer = acceptedOffer
	}
	boolean getAcceptedOffer() {
		acceptedOffer
	}
}
