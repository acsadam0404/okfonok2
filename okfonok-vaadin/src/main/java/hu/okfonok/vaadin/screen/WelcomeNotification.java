package hu.okfonok.vaadin.screen;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.DialogWithCloseButton;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public final class WelcomeNotification extends CustomComponent {

	public static void showOnFirstLogin() {
		User user = Authentication.getUser();
		if (user.isFirstLogin()) {
			String text = "üdv az oldalunkon blablabla";
			
			Dialog dialog = new DialogWithCloseButton(new Label(text), new Button("Értettem, köszi!"));
			dialog.showWindow();
		}
	}
	
	private WelcomeNotification() {
		
	}
}
