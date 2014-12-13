package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;


public class CalendarFrame extends CustomComponent {
	private Calendar calendar;


	public CalendarFrame() {
		setWidth("400px");
		setHeight("250px");
		calendar = build();
		Panel panel = new Panel(calendar);
		panel.setHeight("100%");
		setCompositionRoot(panel);
	}


	private Calendar build() {
		calendar = new Calendar();
		calendar.setWeeklyCaptionFormat("MM.dd");
		calendar.setWidth("400px");
		calendar.setHeight("250px");
		return calendar;
	}
}
