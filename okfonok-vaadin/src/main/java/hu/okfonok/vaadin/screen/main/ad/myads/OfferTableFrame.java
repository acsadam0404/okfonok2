package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;
import hu.okfonok.offer.events.AcceptOfferEvent;
import hu.okfonok.vaadin.UIEventBus;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
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
				return null;
			}
		});
		table.addContainerProperty("actions", Component.class, null);
		table.addGeneratedColumn("actions", new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				final Offer offer = (Offer) itemId;
				VerticalLayout l = new VerticalLayout();
				Button acceptButton = new Button("Elfogadom", new ClickListener() {

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
						l.addComponent(new Button("Mégse fogadom el", new ClickListener() {

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

				l.addComponent(new Button("Üzenetet küldök", new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub

					}
				}));
				return l;
			}
		});
		table.addGeneratedColumn(Offer.INTERVALS, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				VerticalLayout l = new VerticalLayout();
				l.addComponent(new Button("Naptár", new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub

					}
				}));
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
		table.setVisibleColumns(IMAGE, "user.profile.shortenedName", "user.rating", "user.profile.introduction", Offer.AMOUNT, "actions");
		return table;
	}


	@Subscribe
	public void handleAcceptOfferEvent(AcceptOfferEvent event) {
		refresh();
	}


	public void refresh() {
		refresh(ad);
	}


	public void refresh(Advertisement ad) {
		this.ad = ad;
		BeanItemContainer<Offer> container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(ad.getOffers());
	}
}
