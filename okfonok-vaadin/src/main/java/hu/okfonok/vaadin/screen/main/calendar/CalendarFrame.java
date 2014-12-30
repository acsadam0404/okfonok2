package hu.okfonok.vaadin.screen.main.calendar;

import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.events.MainTabChangeEvent;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;


public class CalendarFrame extends CustomComponent {
	private Calendar calendar;


	public CalendarFrame() {
		UIEventBus.register(this);
		setWidth("500px");
		setHeight("250px");
		calendar = build();
		Panel panel = new Panel(calendar);
		panel.setHeight("100%");
		setCompositionRoot(panel);
	}


	private Calendar build() {
		calendar = new Calendar();
		calendar.setWeeklyCaptionFormat("MM.dd");
		calendar.setEventProvider(new SavedAdvertisementEventProvider());
		calendar.setHandler(new EventClickHandler() {
			@Override
			public void eventClick(EventClick event) {
				if (event.getCalendarEvent() instanceof MainCalendarEvent) {
					((MainCalendarEvent) event.getCalendarEvent()).onClick();
				}
			}
		});
		calendar.setWidth("480px");
		calendar.setHeight("600px");
		return calendar;
	}


	@Subscribe
	public void handleMainTabChangeEvent(MainTabChangeEvent event) {
		if (MainTabChangeEvent.TabId.ADS == event.getTabId()) {
			calendar.setEventProvider(new SavedAdvertisementEventProvider());
		}
		else {
			calendar.setEventProvider(new AcceptedOfferEventProvider());
		}
		calendar.markAsDirty();
	}
}
