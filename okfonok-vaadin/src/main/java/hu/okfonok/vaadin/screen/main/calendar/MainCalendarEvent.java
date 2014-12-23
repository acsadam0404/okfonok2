package hu.okfonok.vaadin.screen.main.calendar;

import com.vaadin.ui.components.calendar.event.BasicEvent;


public abstract class MainCalendarEvent extends BasicEvent {
	abstract void onClick();
}
