package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.user.User;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;


public class ProfileImageFrame extends CustomComponent {
	public ProfileImageFrame(User user) {
		setCompositionRoot(new Label(user.getProfile().toString()));
	}
}
