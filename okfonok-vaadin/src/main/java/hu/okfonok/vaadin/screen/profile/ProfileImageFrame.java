package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.user.User;
import hu.okfonok.user.events.ProfileImageUpdatedEvent;
import hu.okfonok.vaadin.UIEventBus;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class ProfileImageFrame extends CustomComponent {
	private User user;

	/**
	 * ezen van rajta az upload gomb és a profilkép
	 */
	private VerticalLayout imageHolder;


	public ProfileImageFrame(User user) {
		assert user != null;
		this.user = user;
		UIEventBus.register(this);
		setCompositionRoot(build());
		refresh();
	}


	private Component build() {
		//TODO ezt úgy kéne megcsinálni, hogy a skillkör közeépre menjen az imageholder
		VerticalLayout l = new VerticalLayout();
		l.addComponent(new Label("/" + user.getRatings().size()));
		imageHolder = new VerticalLayout();
		l.addComponent(imageHolder);
		l.addComponent(new Rating(user));
		SkillCircle skillCircle = new SkillCircle(user);
		l.addComponent(skillCircle);
		return l;
	}


	@Subscribe
	public void handleProfileImageUpdatedEvent(ProfileImageUpdatedEvent event) {
		if (event.getUser().equals(user)) {
			refresh();
		}
	}


	/**
	 * beállíthatjuk readonlyra, ekkor nem jelenik meg a feltöltés
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		refresh();
	}


	private void refresh() {
		imageHolder.removeAllComponents();
		Image profileImage = new Image(null, new FileResource(user.getProfileImageLargePath().toFile()));
		imageHolder.addComponent(profileImage);
		if (!isReadOnly()) {
			imageHolder.addComponent(new ProfileImageUpload(user));
		}
	}

}
