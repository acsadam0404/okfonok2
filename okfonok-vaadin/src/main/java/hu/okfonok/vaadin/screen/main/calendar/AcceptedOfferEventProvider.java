package hu.okfonok.vaadin.screen.main.calendar;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.DateInterval;
import hu.okfonok.offer.Offer;
import hu.okfonok.offer.events.AcceptOfferEvent;
import hu.okfonok.vaadin.security.Authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;


/**
 * Egy hirdetéshez egy elfogadott ajánlat létezik.
 * Egy eflogadott ajánlatban több intervallum lehet.
 * Számunkra nem érdekes, hogy melyiket beszélik meg az intervallumok közül, mindet megjelenítjük.
 */
public class AcceptedOfferEventProvider extends BasicEventProvider {

	public static class Event extends MainCalendarEvent {
		private Offer offer;


		public Event(Offer offer, Date start, Date end) {
			this.offer = offer;
			setStart(start);
			setEnd(end);
			setStyleName("accepted-offer");
		}


		@Override
		void onClick() {
			//TODO
			System.out.println("itt valamit kéne csinálni :)");
		}

	}


	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<>();
		List<Offer> offers = Advertisement.findAcceptedOffers(Authentication.getUser(), startDate, endDate);
		for (Offer offer : offers) {
			for (DateInterval i : offer.getIntervals()) {
				Event event = new Event(offer, i.getStart(), i.getEnd());
				events.add(event);
			}
		}
		return events;
	}


	@Subscribe
	public void handleAcceptOfferEvent(AcceptOfferEvent event) {
		fireEventSetChange();
	}

}
