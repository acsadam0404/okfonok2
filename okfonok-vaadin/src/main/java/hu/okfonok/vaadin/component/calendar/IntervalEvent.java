package hu.okfonok.vaadin.component.calendar;

import hu.okfonok.common.DateInterval;

import com.vaadin.ui.components.calendar.event.BasicEvent;

public class IntervalEvent extends BasicEvent {

	private DateInterval dateInterval;

	public IntervalEvent(DateInterval dateInterval) {
		this.dateInterval = dateInterval;
		setStart(dateInterval.getStart());
		setEnd(dateInterval.getEnd());
	}

	public DateInterval getDateInterval() {
		return dateInterval;
	}

}