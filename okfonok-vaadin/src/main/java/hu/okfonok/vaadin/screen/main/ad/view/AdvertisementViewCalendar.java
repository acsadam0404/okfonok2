package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.DateInterval;
import hu.okfonok.vaadin.screen.main.ad.PreferredIntervalEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

class AdvertisementViewCalendar extends CustomComponent implements CalendarEventProvider{
	private Calendar calendar;
	private Advertisement ad;


	public AdvertisementViewCalendar(Advertisement ad) {
		this.ad = ad;
		calendar = build();
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.addComponent(calendar);
		setCompositionRoot(l);
	}

	private Calendar build() {
		Calendar calendar = new Calendar();
		calendar.setSizeFull();
		calendar.setWeeklyCaptionFormat("MMM dd");
		calendar.setEventProvider(this);
		return calendar;
	}

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<>();
		for (DateInterval di : ad.getPreferredIntervals()) {
			PreferredIntervalEvent pie = new PreferredIntervalEvent(di);
			events.add(pie);
		}
		return events;
	}
}
