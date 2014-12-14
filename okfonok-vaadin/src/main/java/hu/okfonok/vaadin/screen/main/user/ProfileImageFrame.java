package hu.okfonok.vaadin.screen.main.user;

import org.vaadin.teemu.ratingstars.RatingStars;

import hu.okfonok.user.User;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class ProfileImageFrame extends CustomComponent {
	private User user;

	public ProfileImageFrame(User user) {
		this.user = user;
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout l = new VerticalLayout();
		l.addComponent(new SkillCircle(user));
		l.addComponent(new Label(user.getProfile().getName()));
		l.addComponent(new Label("/" + user.getRatings().size()));
		RatingStars stars = new RatingStars();
		stars.setValue(user.getRating());
		stars.setReadOnly(true);
		l.addComponent(stars);
		return l;
	}
}
