package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementCreatedEvent;
import hu.okfonok.vaadin.security.Authentication;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

public class OwnAdvertisementTableFrame extends CustomComponent {
	private static final String ACTIONS = "actions";
	private Table table;
	private OfferTableFrame offers;

	private static class ModifyButton extends Button implements ClickListener {

		private Advertisement ad;

		public ModifyButton(Advertisement ad) {
			super("Módosítom");
			this.ad = ad;
			addClickListener(this);
		}

		@Override
		public void buttonClick(ClickEvent event) {

		}
	}

	private static class ShareButton extends Button implements ClickListener {

		private Advertisement ad;

		public ShareButton(Advertisement ad) {
			super("Megosztom");
			this.ad = ad;
			addClickListener(this);
		}

		@Override
		public void buttonClick(ClickEvent event) {
			ad.share();
		}
	}

	public OwnAdvertisementTableFrame() {
		UIEventBus.register(this);
		table = buildTable();

		VerticalLayout l = new VerticalLayout();
		l.addComponent(table);
		offers = new OfferTableFrame();
		l.addComponent(offers);
		refresh();
		setCompositionRoot(l);
	}

	private void refresh() {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		List<Advertisement> ads = Advertisement.findByUser(Authentication.getUser());
		container.addAll(ads);
	}

	private Table buildTable() {
		Table table = new Table();
		table.setSizeFull();
		
		table.setColumnHeader(Advertisement.MAINCATEGORY, "Főkategória");
		table.setColumnHeader(Advertisement.CATEGORY, "Kategória");
		table.setColumnHeader(Advertisement.DESCRIPTION, "Leírás");
		table.setColumnHeader(Advertisement.REMUNERATION, "Díjazás");
		table.setColumnHeader(Advertisement.AVERAGEPRICE, "Aktuális átlag díj");
		table.setColumnHeader(Advertisement.OFFERSNUMBER, "Ajánlatok száma");
		
		table.addContainerProperty(ACTIONS, Component.class, null);
		table.addGeneratedColumn(ACTIONS, new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, final Object columnId) {
				Advertisement ad = (Advertisement) ((BeanItem) source.getItem(itemId)).getBean();
				VerticalLayout l = new VerticalLayout();
				l.addComponent(new ModifyButton(ad));
				l.addComponent(new ShareButton(ad));
				return l;
			}
		});
		table.setImmediate(true);
		table.setContainerDataSource(new BeanItemContainer<Advertisement>(Advertisement.class));
		table.setSelectable(true);
		table.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Advertisement ad = (Advertisement) event.getProperty().getValue();
				if (ad != null) {
					offers.refresh(ad);
				}
			}
		});
		
		table.setVisibleColumns(Advertisement.MAINCATEGORY,
				Advertisement.CATEGORY,
				Advertisement.DESCRIPTION,
				Advertisement.REMUNERATION,
				Advertisement.AVERAGEPRICE,
				Advertisement.OFFERSNUMBER,
				ACTIONS);
		
		return table;
	}

	@Subscribe
	public void handleAdvertisementCreatedEvent(AdvertisementCreatedEvent event) {
		refresh();
	}
}
