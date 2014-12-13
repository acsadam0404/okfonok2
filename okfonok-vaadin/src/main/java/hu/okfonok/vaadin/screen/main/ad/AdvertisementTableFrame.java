package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.common.Address;
import hu.okfonok.common.Distance;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.math.BigDecimal;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
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
	private Table root;

	public AdvertisementTableFrame() {
		UIEventBus.register(this);
		root = new Table();
		root.setColumnHeader(Advertisement.MAINCATEGORY, "Főkategória");
		root.setColumnHeader(Advertisement.CATEGORY, "Kategória");
		root.setColumnHeader(Advertisement.DESCRIPTION, "Leírás");
		root.setColumnHeader(Advertisement.REMUNERATION, "Díjazás");
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
		root.addContainerProperty(ACTIONS, Component.class, null, "", null, Align.CENTER);
		root.addGeneratedColumn(ACTIONS, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				VerticalLayout l = new VerticalLayout();
				final User user = Authentication.getUser();
				final Advertisement ad = (Advertisement) ((BeanItem)source.getItem(itemId)).getBean();
				if (user.isAdvertisementSaved(ad)) {
					l.addComponent(new Button("Elmentve", new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							user.unsaveAdvertisement(ad);
							refresh();
						}
					}));
				}
				else {
					l.addComponent(new Button("Elmentem", new ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							user.saveAdvertisement(ad);
							refresh();
						}
					}));
				}
				l.addComponent(new Button("Megosztom", new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						ad.share();
					}
				}));
				return l;
			}
		});
	}

	private void addAveragePriceColumn() {
		root.addContainerProperty(AVERAGE_PRICE, String.class, null, "Aktuális átlag díj", null, Align.CENTER);
		root.addGeneratedColumn(AVERAGE_PRICE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Advertisement ad = (Advertisement) ((BeanItem)source.getItem(itemId)).getBean();
				return ad.getAveragePrice() + " Ft";
			}
		});
	}

	private void addDistanceColumn() {
		root.addContainerProperty(DISTANCE, String.class, null, "Távolság", null, Align.CENTER);
		root.addGeneratedColumn(DISTANCE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Address addressInRow = (Address) source.getItem(itemId).getItemProperty(Advertisement.ADDRESS).getValue();
				BigDecimal distance = Distance.between(Authentication.getUser().getAddress(), addressInRow);
				return distance + " km"; 
			}
		});
	}

	private void addImageColumn() {
		root.addContainerProperty(IMAGE, String.class, null, "Kép", null, Align.CENTER);
		root.addGeneratedColumn(IMAGE, new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return "kép helye";
			}
		});
	}

	private void refresh() {
		BeanItemContainer<Advertisement> container = (BeanItemContainer) root.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Advertisement.findAll());
	}

	@Subscribe
	public void handleAdvertisementCreatedEvent(AdvertisementCreatedEvent event) {
		refresh();
	}
}
