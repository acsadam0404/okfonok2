package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.DateInterval;
import hu.okfonok.offer.Offer;
import hu.okfonok.vaadin.component.calendar.IntervalEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;


public final class PlaceOfferCalendar extends CustomComponent implements CalendarEventProvider {
	private Set<DateInterval> intervals = new HashSet<>();

	private Calendar calendar;
	private Advertisement ad;

	/**
	 * ha van preferred interval a hirdetésen, akkor választunk
	 */
	private boolean choose;


	public PlaceOfferCalendar(Advertisement ad) {
		assert ad != null;
		this.ad = ad;

		choose = !ad.getPreferredIntervals().isEmpty();

		build();

		if (choose) {
			initForChooseEvents();
		}
		else {
			initForAddEvents();
		}
	}

	private void build() {
		calendar = buildCalendar();
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.addComponent(calendar);
		setCompositionRoot(l);
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
		if (choose) {
			for (DateInterval di : ad.getPreferredIntervals()) {
				IntervalEvent pie = new IntervalEvent(di);
				events.add(pie);
			}
		}
		else {
			for (DateInterval di : intervals) {
				IntervalEvent pie = new IntervalEvent(di);
				events.add(pie);
			}
		}

		for (CalendarEvent event : events) {
			IntervalEvent ie = (IntervalEvent) event;
			if (intervals.contains(ie.getDateInterval())) {
				ie.setStyleName("accepted-offer-interval");
			}
		}
		return events;
	}


	private void initForAddEvents() {
		calendar.setHandler(new RangeSelectHandler() {
			@Override
			public void rangeSelect(RangeSelectEvent event) {
				DateInterval di = new DateInterval(event.getStart(), event.getEnd());
				intervals.add(di);

				IntervalEvent ie = new IntervalEvent(di);
				ie.setStyleName("accepted-offer-interval");
				calendar.addEvent(ie);
			}
		});
		calendar.setHandler(new EventClickHandler() {

			@Override
			public void eventClick(EventClick event) {
				IntervalEvent ie = (IntervalEvent) event.getCalendarEvent();
				intervals.remove(ie);
			}
		});

		//TODO többi handler
	}


	private void initForChooseEvents() {
		calendar.setHandler(new EventClickHandler() {
			@Override
			public void eventClick(EventClick event) {
				IntervalEvent ie = (IntervalEvent) event.getCalendarEvent();
				if (intervals.contains(ie.getDateInterval())) {
					intervals.remove(ie.getDateInterval());
				}
				else {
					intervals.add(ie.getDateInterval());
				}
			}
		});
	}


	public Set<DateInterval> getIntervals() {
		return intervals;
	}

}