package hu.okfonok.vaadin.screen.landing;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;


public class LandingScreen extends CustomComponent {
	public LandingScreen() {
		CustomLayout landing = new CustomLayout("landing");
		PopupView loginPopup = new PopupView(new Content() {

			@Override
			public String getMinimizedValueAsHTML() {
				return "Bejelentkezés";
			}


			@Override
			public Component getPopupComponent() {
				return new LoginFrame();
			}

		});
		loginPopup.setHideOnMouseOut(false);
		landing.addComponent(loginPopup, "login");

		PopupView registrationPopup = new PopupView(new Content() {

			@Override
			public String getMinimizedValueAsHTML() {
				return "Regisztráció";
			}


			@Override
			public Component getPopupComponent() {
				return new RegistrationFrame();
			}

		});
		landing.addComponent(registrationPopup, "registration");
		registrationPopup.setHideOnMouseOut(false);
		setCompositionRoot(landing);
	}
}
