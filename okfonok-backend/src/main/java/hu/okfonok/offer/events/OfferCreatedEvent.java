package hu.okfonok.offer.events;

import hu.okfonok.offer.Offer;

public class OfferCreatedEvent {
	private Offer offer;

	public OfferCreatedEvent(Offer offer) {
		this.offer = offer;
	}
	
	public Offer getOffer() {
		return offer;
	}
}
