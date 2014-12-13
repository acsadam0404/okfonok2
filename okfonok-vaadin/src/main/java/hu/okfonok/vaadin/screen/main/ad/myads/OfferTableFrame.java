package hu.okfonok.vaadin.screen.main.ad.myads;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;


public class OfferTableFrame extends CustomComponent {
	private static final String IMAGE = "image";
	private Table table;

	public OfferTableFrame() {
		table = buildTable();
		setCompositionRoot(table);
	}

	private Table buildTable() {
		Table table = new Table();
		BeanItemContainer<Offer> container = new BeanItemContainer<Offer>(Offer.class);
		
		container.addNestedContainerProperty("user.profile.shortenedName");
		container.addNestedContainerProperty("user.rating");
		table.addContainerProperty(IMAGE, Component.class, null);
		table.addGeneratedColumn(IMAGE, new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return null;
			}
		});
		
		table.setContainerDataSource(container);		
		
		table.setColumnHeader("user.profile.shortenedName", "Név");
		table.setColumnHeader("user.rating", "Értékelés");
		table.setColumnHeader(IMAGE, "Kép");
		table.setVisibleColumns(IMAGE, "user.profile.shortenedName", "user.rating");
		return table;
	}

	public void refresh(Advertisement ad) {
		BeanItemContainer container = (BeanItemContainer)table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(ad.getOffers());
	}
}
