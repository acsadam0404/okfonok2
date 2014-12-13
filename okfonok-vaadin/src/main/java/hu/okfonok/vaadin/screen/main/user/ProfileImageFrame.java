package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.user.Profile;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;


public class ProfileImageFrame extends CustomComponent {
	public ProfileImageFrame(Profile profile) {
		setCompositionRoot(new Label(profile.toString()));
	}
}
