package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementCreatedEvent;
import hu.okfonok.vaadin.security.Authentication;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class OwnAdvertisementTableFrame extends CustomComponent {
	private Table table;
	private OfferTableFrame offers;

	public OwnAdvertisementTableFrame() {
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
		return table;
	}
	
	//ez kell
//	@Subscribe
//	public void handleAdvertisementCreatedEvent(AdvertisementCreatedEvent event) {
//		refresh();
//	}
}
