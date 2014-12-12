package hu.okfonok.vaadin.screen.landing;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextField;


public class LandingScreen extends CustomComponent {
	public LandingScreen() {
		CustomLayout landing = new CustomLayout("landing");
		landing.addComponent(new LoginFrame(), "login");
		setCompositionRoot(landing);
	}
}
