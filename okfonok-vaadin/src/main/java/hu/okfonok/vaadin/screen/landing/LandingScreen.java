package hu.okfonok.vaadin.screen.landing;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;


public class LandingScreen extends CustomComponent {
	public LandingScreen() {
		CustomLayout landing = new CustomLayout("landing");
		landing.addComponent(new LoginFrame(), "login");
		landing.addComponent(new RegistrationFrame(), "registration");
		setCompositionRoot(landing);
	}
}
