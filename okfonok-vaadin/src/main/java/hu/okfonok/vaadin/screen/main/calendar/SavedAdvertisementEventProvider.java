package hu.okfonok.vaadin.screen.main.calendar;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.events.AdvertisementSaveEvent;
import hu.okfonok.common.DateInterval;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.ad.view.AdvertisementViewFrame;
import hu.okfonok.vaadin.security.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;


/**
 * Felhasználó elmenthet hirdetéseket és azok megjelennek a naptárában.
 * Egy hirdetéshez több intervallum lehetséges, ezeket külön eventekként megjelenítjük.
 */
public class SavedAdvertisementEventProvider extends BasicEventProvider {

	public SavedAdvertisementEventProvider() {
		UIEventBus.register(this);
	}


	public static class Event extends MainCalendarEvent {
		private Advertisement ad;

		public Event(Advertisement ad, Date start, Date end) {
			this.ad = ad;
			setStart(start);
			setEnd(end);
			setStyleName("saved-ad");
		}


		@Override
		void onClick() {
			new Dialog(new AdvertisementViewFrame(ad)).showWindow();
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


	@Subscribe
	public void handleAdvertisementSaveEvent(AdvertisementSaveEvent event) {
		fireEventSetChange();
	}

}
