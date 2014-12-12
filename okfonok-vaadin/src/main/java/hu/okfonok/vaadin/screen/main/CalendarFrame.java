package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;


public class CalendarFrame extends CustomComponent {
	private Calendar calendar;


	public CalendarFrame() {
		setWidth("400px");
		setHeight("400px");
		calendar = build();
		setCompositionRoot(calendar);
	}


	private Calendar build() {
		calendar = new Calendar();
		calendar.setWidth("400px");
		calendar.setHeight("400px");
		return calendar;
	}
}
