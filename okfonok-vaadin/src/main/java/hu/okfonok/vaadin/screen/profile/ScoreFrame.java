package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.user.User;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;


public class ScoreFrame extends CustomComponent {
	public ScoreFrame(User user) {
		setCompositionRoot(new Label("grafikon"));
	}
}
