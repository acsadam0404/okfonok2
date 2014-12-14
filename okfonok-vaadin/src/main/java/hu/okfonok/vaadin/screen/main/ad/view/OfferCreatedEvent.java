package hu.okfonok.vaadin.screen.main.ad.view;

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
