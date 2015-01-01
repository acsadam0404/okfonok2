package hu.okfonok.vaadin.screen.menu;

import hu.okfonok.user.notification.Notification;
import hu.okfonok.user.notification.events.NotificationEvent;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.util.Set;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;

public class NotificationFrame extends CustomComponent {
	
	
	private PopupView pv;

	public NotificationFrame() {
		UIEventBus.register(this);
		setCompositionRoot(build());
		refresh();
	}

	private Component build() {
		Set<Notification> notifications = Authentication.getUser().getUnreadNotifications();
		pv = new PopupView("", new Label());
		pv.setHideOnMouseOut(false);
		HorizontalLayout hl = new HorizontalLayout();
		Image image = new Image(null, new ThemeResource("img/landing/regisztralj.png"));
		hl.addLayoutClickListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				pv.setPopupVisible(true);				
			}
		});
		hl.addComponent(image);
		hl.addComponent(new Label(Integer.toString(notifications.size())));
		hl.addComponent(pv);
		return hl;
	}
	
	@Subscribe
	public void handleNotificationEvent(NotificationEvent event) {
		refresh();
	}
	
	public void refresh() {
		pv.setContent(new Content() {
			@Override
			public Component getPopupComponent() {
				Set<Notification> notifications = Authentication.getUser().getUnreadNotifications();
				if (notifications.isEmpty()) {
					return new Label("Nincs notifikésön");
				}
				
				VerticalLayout root = new VerticalLayout();
				for (Notification n : notifications) {
					root.addComponent(new NotitifactionRow(n));
				}
				
				return root;
			}
			
			@Override
			public String getMinimizedValueAsHTML() {
				return "";
			}
		});
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
