package hu.okfonok.vaadin.component.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.components.calendar.event.BasicEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider.EventSetChangeListener;


/**
 * Több CalendarEventProvider-t összefog, mivel a calendarra csak egyet lehet rátenni.
 * Implementálja az EventSetChangeListener-t, regisztrálja magát a belső providereknél és továbbítja az eventSetChanget a calendar felé.
 */
public class EventProvidersCollection extends BasicEventProvider implements EventSetChangeListener {

	private CalendarEventProvider[] providers;


	public EventProvidersCollection(CalendarEventProvider... providers) {
		this.providers = providers;
		for (CalendarEventProvider provider : providers) {
			if (provider instanceof EventSetChangeNotifier) {
				((EventSetChangeNotifier) provider).addEventSetChangeListener(this);
			}
		}
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


	@Override
	public void eventSetChange(EventSetChangeEvent changeEvent) {
		fireEventSetChange();
	}
}
