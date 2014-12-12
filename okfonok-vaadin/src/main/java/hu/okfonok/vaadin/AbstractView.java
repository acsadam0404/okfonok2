package hu.okfonok.vaadin;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;


public abstract class AbstractView extends CustomComponent implements View {
	public AbstractView() {
		setSizeFull();
		addStyleName("view");
	}
}
