package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class Footer extends CustomComponent {
	public Footer() {
		setCompositionRoot(new Label("footer"));
		setHeight("100px");
	}
}
