package hu.okfonok.vaadin.screen.menu;

import hu.okfonok.user.notification.Notification;
import hu.okfonok.vaadin.security.Authentication;

import java.util.Set;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;

public class NotificationFrame extends CustomComponent {
	public NotificationFrame() {
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout l = new VerticalLayout();
		final Set<Notification> notifications = Authentication.getUser().getUnreadNotifications();
		PopupView pv = new PopupView(new Content() {
			
			@Override
			public Component getPopupComponent() {
				VerticalLayout l = new VerticalLayout();
				for (Notification n : notifications) {
					l.addComponent(new NotitifactionRow(n));
				}
				return l;
			}
			
			@Override
			public String getMinimizedValueAsHTML() {
				return Integer.toString(notifications.size());
			}
		});
		return l;
	}
	
	private static class NotitifactionRow extends CustomComponent {

		private Notification notification;

		public NotitifactionRow(Notification notification) {
			assert notification != null;
			this.notification = notification;
			setCompositionRoot(new Label("noti:"+ notification.getId()));
		}


	}

}
