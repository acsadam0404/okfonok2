package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.events.AdvertisementCreatedEvent;
import hu.okfonok.ad.events.AdvertisementSaveEvent;
import hu.okfonok.common.Address;
import hu.okfonok.common.Distance;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.ad.view.AdvertisementViewFrame;
import hu.okfonok.vaadin.security.Authentication;

import java.math.BigDecimal;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;


public class AdvertisementTableFrame extends CustomComponent {
	private static final String ACTIONS = "actions";
	private static final String DISTANCE = "distance";
	private static final String IMAGE = "image";
	private static final String AVERAGE_PRICE = "averagePrice";
	private Table root; //TODO átnevezni table-re


	public AdvertisementTableFrame() {
		UIEventBus.register(this);

		//TODO refactor build metódusba 
		root = new Table();
		root.setColumnHeader(Advertisement.MAINCATEGORY, "Főkategória");
		root.setColumnHeader(Advertisement.CATEGORY, "Kategória");
		root.setColumnHeader(Advertisement.DESCRIPTION, "Leírás");
		root.setColumnHeader(Advertisement.REMUNERATION, "Díjazás");
		root.addContainerProperty(ACTIONS, Component.class, null, "", null, Align.CENTER);
		root.addContainerProperty(AVERAGE_PRICE, String.class, null, "Aktuális átlag díj", null, Align.CENTER);
		root.addContainerProperty(DISTANCE, String.class, null, "Távolság", null, Align.CENTER);
		root.addContainerProperty(IMAGE, Component.class, null, "Kép", null, Align.CENTER);

		root.setColumnExpandRatio(Advertisement.DESCRIPTION, 1f);
		addImageColumn();
		addDistanceColumn();
		addAveragePriceColumn();
		addActionsColumn();

		root.setContainerDataSource(new BeanItemContainer<Advertisement>(Advertisement.class));
		root.setVisibleColumns(IMAGE, Advertisement.MAINCATEGORY, Advertisement.CATEGORY, Advertisement.DESCRIPTION, Advertisement.REMUNERATION, AVERAGE_PRICE, DISTANCE, ACTIONS);
		root.setSizeFull();
		refresh();
		setCompositionRoot(root);
	}


	private void addActionsColumn() {

		root.addGeneratedColumn(ACTIONS, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				VerticalLayout l = new VerticalLayout();
				l.setSpacing(true);
				final User user = Authentication.getUser();
				BeanItem<Advertisement> item = (BeanItem) source.getItem(itemId);
				if (item != null) {
					final Advertisement ad = item.getBean();
					if (user.isAdvertisementSaved(ad)) {
						l.addComponent(new DeleteButton(ad));
					}
					else {
						l.addComponent(new BookmarkButton(ad));
					}
					l.addComponent(new ShareButton(ad));
				}
				return l;
			}
		});
	}


	private static class BookmarkButton extends Button implements ClickListener {
		private Advertisement ad;


		public BookmarkButton(Advertisement ad) {
			this.ad = ad;
			setCaption("Elmentem");
			setIcon(FontAwesome.STAR);
			addClickListener(this);
		}


		@Override
		public void buttonClick(ClickEvent event) {
			Authentication.getUser().saveAdvertisement(ad);
			UIEventBus.post(new AdvertisementSaveEvent(true));
		}
	}


	private static class DeleteButton extends Button implements ClickListener {
		private Advertisement ad;


		public DeleteButton(Advertisement ad) {
			this.ad = ad;
			setCaption("Törlöm");
			setIcon(FontAwesome.ERASER);
			addClickListener(this);
		}


		@Override
		public void buttonClick(ClickEvent event) {
			Authentication.getUser().unsaveAdvertisement(ad);
			UIEventBus.post(new AdvertisementSaveEvent(false));
		}
	}


	private static class ShareButton extends Button implements ClickListener {
		private Advertisement ad;


		public ShareButton(Advertisement ad) {
			this.ad = ad;
			setCaption("Megosztom");
			setIcon(FontAwesome.SHARE);
			addClickListener(this);
		}


		@Override
		public void buttonClick(ClickEvent event) {
			ad.share();
		}
	}


	@Subscribe
	public void handleAdvertisementSaveEvent(AdvertisementSaveEvent event) {
		refresh();
	}


	private void addAveragePriceColumn() {

		root.addGeneratedColumn(AVERAGE_PRICE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				BeanItem<Advertisement> item = (BeanItem) source.getItem(itemId);
				if (item != null) {
					Advertisement ad = item.getBean();
					return ad.getAveragePrice() + " Ft";
				}
				return null;
			}
		});
	}


	private void addDistanceColumn() {

		root.addGeneratedColumn(DISTANCE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Item item = source.getItem(itemId);
				if (item != null) {
					Address addressInRow = (Address) item.getItemProperty(Advertisement.ADDRESS).getValue();
					BigDecimal distance = Distance.between(Authentication.getUser().getAddress(), addressInRow);
					return distance + " km";
				}
				return null;
			}
		});
	}


	private void addImageColumn() {

		root.addGeneratedColumn(IMAGE, new ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				//TODO ne legyen neve, hanem csak ikonja: az ad képei közül az első. Ha nincs kép kell egy defaultot használni. 
				return new Button("Megtekint", new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						Advertisement ad = ((BeanItem<Advertisement>) source.getItem(itemId)).getBean();
						new Dialog(new AdvertisementViewFrame(ad)).showWindow();
					}
				});
			}
		});
	}


	private void refresh() {
		BeanItemContainer<Advertisement> container = (BeanItemContainer) root.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Advertisement.findAll());
		//TODO sajátunkat nem szabad itt látni
	}


	@Subscribe
	public void handleAdvertisementCreatedEvent(AdvertisementCreatedEvent event) {
		refresh();
	}
}
