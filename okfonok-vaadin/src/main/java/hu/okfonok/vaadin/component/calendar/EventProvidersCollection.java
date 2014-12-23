package hu.okfonok.vaadin.component.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;


public class EventProvidersCollection implements CalendarEventProvider {

	private CalendarEventProvider[] providers;


	public EventProvidersCollection(CalendarEventProvider... providers) {
		this.providers = providers;
		assert providers != null;
	}


	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<>();
		for (CalendarEventProvider provider : providers) {
			events.addAll(provider.getEvents(startDate, endDate));
		}
		return events;
	}

}
