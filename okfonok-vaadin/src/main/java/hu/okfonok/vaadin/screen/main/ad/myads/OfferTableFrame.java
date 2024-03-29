package hu.okfonok.vaadin.screen.main.ad.myads;

import java.nio.file.Path;

import hu.okfonok.Config;
import hu.okfonok.ad.Advertisement;
import hu.okfonok.message.Conversation;
import hu.okfonok.offer.Offer;
import hu.okfonok.offer.events.AcceptOfferEvent;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.message.MessageBox;
import hu.okfonok.vaadin.screen.profile.ProfileViewFrame;
import hu.okfonok.vaadin.security.Authentication;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;


public class OfferTableFrame extends CustomComponent {
	private static final String IMAGE = "image";
	private Table table;
	private Advertisement ad;


	public OfferTableFrame() {
		UIEventBus.register(this);
		table = buildTable();
		setCompositionRoot(table);
	}


	private Table buildTable() {
		Table table = new Table();
		table.setSizeFull();
		BeanItemContainer<Offer> container = new BeanItemContainer<>(Offer.class);

		container.addNestedContainerProperty("user.profile.shortenedName");
		container.addNestedContainerProperty("user.rating");
		container.addNestedContainerProperty("user.profile.introduction");
		table.addContainerProperty(IMAGE, Component.class, null);
		table.addGeneratedColumn(IMAGE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Offer offer = (Offer) itemId;
				Path profileImagePath = offer.getUser().getProfileImageSmallPath();
				Image e = new Image(profileImagePath.toString(), new FileResource(profileImagePath.toFile()));
				e.addClickListener(new MouseEvents.ClickListener() {

					@Override
					public void click(MouseEvents.ClickEvent event) {
						new Dialog(new ProfileViewFrame(offer.getUser())).showWindow();
					}

				});

				return e;
			}
		});
		table.addContainerProperty("actions", Component.class, null);
		table.addGeneratedColumn("actions", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				final Offer offer = (Offer) itemId;
				VerticalLayout l = new VerticalLayout();
				Button acceptButton = new Button("Elfogadom", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						offer.accept();
						/* TODO ennek domainban a helye */
						UIEventBus.post(new AcceptOfferEvent(offer));
					}
				});
				if (ad.hasAcceptedOffer()) {
					if (!ad.getAcceptedOffer().equals(offer)) {
						acceptButton.setEnabled(false);
						l.addComponent(acceptButton);
					}
					else {
						l.addComponent(new Button("Mégse fogadom el", new Button.ClickListener() {

							@Override
							public void buttonClick(ClickEvent event) {
								offer.reject();
								/* TODO ennek domainban a helye */
								UIEventBus.post(new AcceptOfferEvent(offer));
							}

						}));
					}
				}
				else {
					l.addComponent(acceptButton);
				}

				l.addComponent(new Button("Üzenetet küldök", new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						new MessageBox(Conversation.findOrCreate(Authentication.getUser(), offer.getUser(), ad)).showWindow();
					}
				}));
				return l;
			}
		});
		table.addGeneratedColumn(Offer.INTERVALS, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final Offer offer = (Offer) itemId;
				VerticalLayout l = new VerticalLayout();
				Button calendarButton = new Button(null, new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						new Dialog(new OfferCalendar(offer)).showWindow();
					}
				});
				calendarButton.setIcon(FontAwesome.CALENDAR);
				calendarButton.setStyleName(ValoTheme.BUTTON_HUGE);
				l.addComponent(calendarButton);
				return l;
			}
		});

		table.setContainerDataSource(container);

		table.setColumnHeader("user.profile.shortenedName", "Név");
		table.setColumnHeader("user.rating", "Értékelés");
		table.setColumnHeader("user.profile.introduction", "Bemutatkozás");
		table.setColumnHeader(Offer.INTERVALS, "Naptár");
		table.setColumnHeader(Offer.AMOUNT, "Ajánlat");
		table.setColumnHeader("actions", "");
		table.setColumnHeader(IMAGE, "Kép");
		table.setVisibleColumns(IMAGE, "user.profile.shortenedName", "user.rating", "user.profile.introduction", Offer.AMOUNT, Offer.INTERVALS, "actions");
		return table;
	}


	@Subscribe
	public void handleAcceptOfferEvent(AcceptOfferEvent event) {
		refresh();
	}


	public void refresh() {
		if (ad != null) {
			refresh(ad);
		}
	}


	public void refresh(Advertisement ad) {
		this.ad = ad;
		BeanItemContainer<Offer> container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(ad.getOffers());
	}
}
