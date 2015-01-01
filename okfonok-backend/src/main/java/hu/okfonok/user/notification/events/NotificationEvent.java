package hu.okfonok.user.notification.events;

import hu.okfonok.user.notification.Notification;

public class NotificationEvent {
	private Notification notification;

	public NotificationEvent(Notification notification) {
		this.notification = notification;
	}

	public Notification getNotification() {
		return notification;
	}

}
