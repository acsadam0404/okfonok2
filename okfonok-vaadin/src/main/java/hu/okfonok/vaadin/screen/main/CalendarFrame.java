package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;


public class CalendarFrame extends CustomComponent {
	public CalendarFrame() {
		setCompositionRoot(new Calendar());
	}
}
