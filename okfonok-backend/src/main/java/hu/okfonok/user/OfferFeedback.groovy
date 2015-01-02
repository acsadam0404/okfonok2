package hu.okfonok.user;

import groovy.transform.EqualsAndHashCode
import hu.okfonok.offer.Offer

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "offerfeedback")
@EqualsAndHashCode(includes = ["offer"])
public class OfferFeedback {
	
	@OneToOne
	Offer offer
	
	@ElementCollection
	Double accuracyRating
	
	@ElementCollection
	Double mainRating
	
	@ElementCollection
	Double reliabilityRating
	
	@ElementCollection
	Double qualityRating
	
	Double time
	
}
