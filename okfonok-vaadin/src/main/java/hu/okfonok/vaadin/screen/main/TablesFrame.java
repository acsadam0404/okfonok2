package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;


public class TablesFrame extends CustomComponent {
	public TablesFrame() {
		setSizeFull();
		TabSheet ts = new TabSheet();
		ts.addTab(new AdvertisementTableFrame(), "Hirdetések");
		ts.addTab(new OfferTableFrame(), "Ajánlatok");
		setCompositionRoot(ts);
	}
}
