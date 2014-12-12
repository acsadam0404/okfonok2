package hu.okfonok.vaadin.screen.main;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;


public class TablesFrame extends CustomComponent {
	public TablesFrame() {
		TabSheet ts = new TabSheet();
		ts.addComponent(new OfferTableFrame());

		setCompositionRoot(ts);
	}
}
