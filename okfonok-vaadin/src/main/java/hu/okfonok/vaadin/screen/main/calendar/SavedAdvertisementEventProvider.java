package hu.okfonok.vaadin.screen.main.calendar;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.DateInterval;
import hu.okfonok.vaadin.security.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;


/**
 * Felhasználó elmenthet hirdetéseket és azok megjelennek a naptárában.
 * Egy hirdetéshez több intervallum lehetséges, ezeket külön eventekként megjelenítjük.
 */
public class SavedAdvertisementEventProvider implements CalendarEventProvider {
	public static class Event extends BasicEvent {
		private Advertisement ad;


		public Event(Advertisement ad, Date start, Date end) {
			this.ad = ad;
			setStart(start);
			setEnd(end);
			setStyleName("saved-ad");
		}
	}


	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<>();
		Collection<Advertisement> savedAds = Authentication.getUser().getSavedAds();
		for (Advertisement ad : savedAds) {
			for (DateInterval i : ad.getPreferredIntervals()) {
				events.add(new Event(ad, i.getStart(), i.getEnd()));
			}
		}
		return events;
	}

}