package hu.okfonok.vaadin.screen.main;

import hu.okfonok.ad.Advertisement;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;


public class AdvertisementTableFrame extends CustomComponent {
	private Table root;


	public AdvertisementTableFrame() {
		root = new Table();
		root.setSizeFull();
		root.setContainerDataSource(new BeanItemContainer<Advertisement>(Advertisement.class));
		refresh();
		setCompositionRoot(root);
	}


	private void refresh() {
		BeanItemContainer<Advertisement> container = (BeanItemContainer) root.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Advertisement.findAll());
	}
}
