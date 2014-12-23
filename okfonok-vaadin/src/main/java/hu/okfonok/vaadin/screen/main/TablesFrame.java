package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.component.LazyTabSheet;
import hu.okfonok.vaadin.component.LazyTabSheet.LazyTab;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementTableFrame;
import hu.okfonok.vaadin.screen.main.ad.myads.OwnAdvertisementTableFrame;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;


public class TablesFrame extends CustomComponent {

	private LazyTab tab1 = new LazyTab() {
		@Override
		public Component build() {
			return new AdvertisementTableFrame();
		};
	};

	private LazyTab tab2 = new LazyTab() {
		@Override
		public Component build() {
			return new OwnAdvertisementTableFrame();
		};
	};


	public TablesFrame() {
		TabSheet ts = new LazyTabSheet();
		ts.addTab(tab1, "Feladatok");
		ts.addTab(tab2, "Hirdetéseim");
		setCompositionRoot(ts);
	}
}
