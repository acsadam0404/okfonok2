package hu.okfonok.vaadin.screen.menu;

import java.lang.ref.WeakReference;

import hu.okfonok.GlobalEventBus;
import hu.okfonok.message.events.MessageSentEvent;
import hu.okfonok.vaadin.MainUI;
import hu.okfonok.vaadin.screen.message.MessageView;
import hu.okfonok.vaadin.security.Authentication;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.common.eventbus.Subscribe;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class MessageFrame extends CustomComponent {
	private Label unreadMessageCountLabel = new Label();

	public MessageFrame() {
		GlobalEventBus.register(this);
		setCompositionRoot(build());
	}

	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(unreadMessageCountLabel);
		Image image = new Image(null, new ThemeResource("img/landing/regisztralj.png"));
		hl.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				MainUI.getCurrent().getNavigator().navigateTo(MessageView.NAME);
			}
		});
		hl.addComponent(image);
		return hl;
	}

	private void refresh() {
		/* TODO */
		unreadMessageCountLabel.setValue(RandomStringUtils.randomNumeric(2));
	}

	/**
	 * ha közünk van az eventhez akkor frissítsük
	 * 
	 * @param event
	 */
	@Subscribe
	public void handleMessageSentEvent(MessageSentEvent event) {
		if (event.getConversation().getUser1().equals(Authentication.getUser()) || event.getConversation().getUser2().equals(Authentication.getUser())) {
			if (isAttached()) {
				getUI().access(new Runnable() {

					@Override
					public void run() {
						refresh();
					}

				});
			}
		}
	}
}
