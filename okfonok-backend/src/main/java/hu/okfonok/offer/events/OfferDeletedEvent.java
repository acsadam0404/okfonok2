package hu.okfonok.offer.events;

import hu.okfonok.offer.Offer;


public class OfferDeletedEvent {
	private Offer offer;


	public OfferDeletedEvent(Offer offer) {
		this.offer = offer;
	}


	public Offer getOffer() {
		return offer;
	}

}
