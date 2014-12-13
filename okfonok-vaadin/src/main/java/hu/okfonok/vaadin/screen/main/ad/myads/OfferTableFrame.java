package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;


public class OfferTableFrame extends CustomComponent {
	private Table table;

	public OfferTableFrame() {
		table = buildTable();
		setCompositionRoot(table);
	}

	private Table buildTable() {
		Table table = new Table();
		table.setContainerDataSource(new BeanItemContainer<Offer>(Offer.class));
		return table;
	}

	public void refresh(Advertisement ad) {
		BeanItemContainer container = (BeanItemContainer)table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(ad.getOffers());
	}
}
