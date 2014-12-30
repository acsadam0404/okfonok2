package hu.okfonok.user.events;

import hu.okfonok.user.User;


public class ProfileImageUpdatedEvent {
	private User user;


	public ProfileImageUpdatedEvent(User user) {
		this.user = user;
	}


	public User getUser() {
		return user;
	}

}
