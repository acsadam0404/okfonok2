package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.common.DateInterval;
import hu.okfonok.offer.Offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;


public class OfferCalendar extends CustomComponent {
	private Calendar calendar = new Calendar();


	public OfferCalendar(Offer offer) {
		calendar.setEventProvider(new OfferCalendarEventProvider(offer));
		calendar.setHandler((DateClickHandler) null);
		calendar.setWidth("800px");
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.addComponent(calendar);
		setCompositionRoot(l);
	}


	private static class OfferCalendarEventProvider implements CalendarEventProvider {

		private Offer offer;


		public OfferCalendarEventProvider(Offer offer) {
			this.offer = offer;
		}


		@Override
		public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
			List<CalendarEvent> events = new ArrayList<>();
			for (DateInterval i : offer.getIntervals()) {
				BasicEvent event = new BasicEvent("", "", i.getStart(), i.getEnd());
				events.add(event);
			}
			return events;
		}

	}
}
