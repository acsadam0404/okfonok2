package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.screen.main.ad.AdvertisementTableFrame;
import hu.okfonok.vaadin.screen.main.ad.myads.OfferTableFrame;
import hu.okfonok.vaadin.screen.main.ad.myads.OwnAdvertisementTableFrame;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;


public class TablesFrame extends CustomComponent {
	public TablesFrame() {
		setSizeFull();
		TabSheet ts = new TabSheet();
		ts.addTab(new AdvertisementTableFrame(), "Feladatok");
		ts.addTab(new OwnAdvertisementTableFrame(), "Hirdetéseim");
		setCompositionRoot(ts);
	}
}
