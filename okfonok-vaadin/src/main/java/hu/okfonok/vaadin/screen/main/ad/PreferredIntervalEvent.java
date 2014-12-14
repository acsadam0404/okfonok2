package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.common.DateInterval;

import com.vaadin.ui.components.calendar.event.BasicEvent;

public class PreferredIntervalEvent extends BasicEvent {

	private DateInterval dateInterval;

	public PreferredIntervalEvent(DateInterval dateInterval) {
		this.dateInterval = dateInterval;
		setStart(dateInterval.getStart());
		setEnd(dateInterval.getEnd());
	}

	public DateInterval getDateInterval() {
		return dateInterval;
	}

}