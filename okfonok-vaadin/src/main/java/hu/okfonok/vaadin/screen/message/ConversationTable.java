package hu.okfonok.vaadin.screen.message;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.message.Conversation;
import hu.okfonok.message.events.MessageSentEvent;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.security.Authentication;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;


/**
 * A bejelentkezett felhasználóhoz tartozó beszélgetések.
 * 
 */
public class ConversationTable extends CustomComponent {
	private static final String AD_DESCRIPTION = Conversation.ADVERTISEMENT + "." + Advertisement.DESCRIPTION;
	private static final String ACTIONS = "actions";
	private Table table;


	public ConversationTable() {
		this.table = buildTable();
		setCompositionRoot(table);
		refresh();
	}


	private Table buildTable() {
		Table table = new Table();
		table.setSizeFull();
		table.addContainerProperty(ACTIONS, Component.class, null, "", null, Align.CENTER);

		table.addGeneratedColumn(ACTIONS, new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, final Object columnId) {
				VerticalLayout l = new VerticalLayout();
				l.setSpacing(true);

				Button viewButton = new Button(null, new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						Conversation conv = (Conversation) itemId;
						MessageBox messageBox = new MessageBox(conv);
						Dialog dialog = new Dialog(messageBox);
						dialog.setCaption("Üzenet küldése");
						dialog.setHeight("60%");
						dialog.setWidth("60%");
						dialog.showWindow();
					}
				});
				viewButton.setIcon(FontAwesome.VINE);
				l.addComponent(viewButton);
				return l;
			}
		});

		BeanItemContainer<Conversation> container = new BeanItemContainer<>(Conversation.class);
		container.addNestedContainerProperty(AD_DESCRIPTION);
		table.setContainerDataSource(container);
		table.setVisibleColumns(ACTIONS, Conversation.USER1, Conversation.USER2, Conversation.MESSAGECOUNT, AD_DESCRIPTION, Conversation.DATUM);
		table.setColumnHeader(Conversation.USER1, "Feladó");
		//TODO itt csak az egyik félt kell megmutatni, mert másik nyílván a bejelentkezett user
		table.setColumnHeader(Conversation.USER2, "Feladó2");
		table.setColumnHeader(AD_DESCRIPTION, "Feladat");
		table.setColumnHeader(Conversation.MESSAGECOUNT, "Üzenetek");
		table.setColumnHeader(Conversation.DATUM, "Dátum");
		table.setColumnHeader(ACTIONS, "");
		table.setColumnExpandRatio(AD_DESCRIPTION, 1f);
		return table;
	}


	/**
	 * ha közünk van az eventhez akkor frissítsük
	 * 
	 * @param event
	 */
	@Subscribe
	public void handleMessageSentEvent(MessageSentEvent event) {
		if (event.getConversation().getUser1().equals(Authentication.getUser()) || event.getConversation().getUser2().equals(Authentication.getUser())) {
			refresh();
		}
	}

	public void refresh() {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Conversation.find(Authentication.getUser()));
	}
}
