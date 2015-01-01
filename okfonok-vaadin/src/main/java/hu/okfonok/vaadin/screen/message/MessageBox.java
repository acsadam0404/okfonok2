package hu.okfonok.vaadin.screen.message;

import hu.okfonok.GlobalEventBus;
import hu.okfonok.message.Conversation;
import hu.okfonok.message.Message;
import hu.okfonok.message.events.MessageSentEvent;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import org.apache.commons.lang3.StringUtils;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class MessageBox extends CustomComponent {

	private TextArea messageField;

	private Conversation conversation;

	/**
	 * ezen a layouton vannak az üzenetek, ez frissül be a refreshre
	 */
	private VerticalLayout messages = new VerticalLayout();

	private Panel messagesPanel;

	private class SendButton extends Button {
		public SendButton() {
			setCaption("Küldés");
			setIcon(FontAwesome.SEND);
			setHeight("100%");
			addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					String text = messageField.getValue();
					if (StringUtils.isNotBlank(text)) {
						conversation = Conversation.sendMessage(Authentication.getUser(), conversation.getOtherUser(Authentication.getUser()), conversation.getAdvertisement(), text);
						/* TODO ez is bus rétegbe való */
						UIEventBus.post(new MessageSentEvent(conversation));
						messageField.setValue(null);
					}
				}
			});
		}
	}

	public MessageBox() {
		this(null);
	}

	public MessageBox(Conversation conversation) {
		assert conversation != null;
		setSizeFull();
		GlobalEventBus.register(this);
		this.conversation = conversation;

		messages.setSpacing(true);
		messages.setWidth("100%");
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setMargin(true);
		l.setSpacing(true);

		messagesPanel = new Panel(messages);
		messagesPanel.setSizeFull();
		l.addComponent(messagesPanel);
		messageField = new TextArea();
		messageField.setImmediate(true);
		messageField.setNullRepresentation("");
		messageField.setInputPrompt("Ide írd az üzeneted");
		messageField.setSizeFull();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.setSpacing(true);
		hl.addComponent(messageField);
		SendButton sendButton = new SendButton();
		hl.addComponent(sendButton);
		hl.setExpandRatio(messageField, 1f);
		l.addComponent(hl);
		hl.setComponentAlignment(sendButton, Alignment.MIDDLE_RIGHT);
		l.setExpandRatio(hl, 0.2f);
		l.setExpandRatio(messagesPanel, 0.8f);
		setCompositionRoot(l);
		refresh();

	}

	@Subscribe
	public void handleMessageSentEvent(MessageSentEvent event) {
		// if (conversation != null && event.getConversation().equals(conversation)) {
		if (isAttached()) {
			getUI().access(new Runnable() {

				@Override
				public void run() {
					refresh();
				}

			});
		}
		// }
	}

	public void refresh() {
		messages.removeAllComponents();
		if (conversation != null) {
			for (Message msg : conversation.getMessages()) {
				if (msg.getSender().equals(Authentication.getUser())) {
					MessageArea messageArea = new MessageArea(msg, MessageArea.Direction.LEFT);
					messages.addComponent(messageArea);
					messages.setComponentAlignment(messageArea, Alignment.MIDDLE_LEFT);
				}
				else {
					MessageArea messageArea = new MessageArea(msg, MessageArea.Direction.RIGHT);
					messages.addComponent(messageArea);
					messages.setComponentAlignment(messageArea, Alignment.MIDDLE_RIGHT);
				}
			}
		}
		/* TODO ez nem scrolloz */
		messagesPanel.setScrollTop(Integer.MAX_VALUE);
	}

	public void showWindow() {
		Dialog dialog = new Dialog(this);
		dialog.setWidth("60%");
		dialog.setHeight("60%");
		dialog.setCaption("Üzenet küldése");
		dialog.showWindow();
	}
}
