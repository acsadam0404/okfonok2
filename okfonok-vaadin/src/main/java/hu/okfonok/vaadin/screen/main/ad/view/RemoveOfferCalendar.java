package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.common.DateInterval;
import hu.okfonok.offer.Offer;
import hu.okfonok.vaadin.component.calendar.IntervalEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;


/**
 * Egy ajánlathoz mutatja meg az időpontokat. Csak megtekintésre való, itt nincsenek handlerek.
 * 
 */
final class RemoveOfferCalendar extends CustomComponent implements CalendarEventProvider {
	private Offer offer;


	public RemoveOfferCalendar(Offer offer) {
		assert offer != null;
		this.offer = offer;

		setCompositionRoot(build());
	}


	private Component build() {
		Calendar calendar = buildCalendar();
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.addComponent(calendar);
		return l;
	}


	private Calendar buildCalendar() {
		Calendar calendar = new Calendar();
		calendar.setHandler((DateClickHandler) null);
		calendar.setSizeFull();
		calendar.setWeeklyCaptionFormat("MMM dd");
		calendar.setEventProvider(this);
		return calendar;
	}


	/**
	 * IntervalEvent set-et ad vissza, sose nullt
	 */
	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<>();
		for (DateInterval di : offer.getIntervals()) {
			events.add(new IntervalEvent(di));
		}
		return events;
	}
}
