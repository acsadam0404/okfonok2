package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.user.User;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;


public class MessageBox extends CustomComponent {
	public MessageBox(Advertisement ad) {
		setCompositionRoot(new Label("messagebox"));
	}
}
