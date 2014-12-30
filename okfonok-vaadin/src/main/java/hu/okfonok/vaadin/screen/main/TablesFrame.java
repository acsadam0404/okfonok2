package hu.okfonok.vaadin.screen.main;

import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.component.LazyTabSheet;
import hu.okfonok.vaadin.component.LazyTabSheet.LazyTab;
import hu.okfonok.vaadin.screen.main.ad.AdvertisementTableFrame;
import hu.okfonok.vaadin.screen.main.ad.myads.OwnAdvertisementTableFrame;
import hu.okfonok.vaadin.screen.main.events.MainTabChangeEvent;
import hu.okfonok.vaadin.screen.main.events.MainTabChangeEvent.TabId;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.ValoTheme;


public class TablesFrame extends CustomComponent {

	private LazyTab tab1 = new LazyTab() {
		@Override
		public Component build() {
			UIEventBus.post(new MainTabChangeEvent(TabId.ADS));
			return new AdvertisementTableFrame();
		};
	};

	private LazyTab tab2 = new LazyTab() {
		@Override
		public Component build() {
			UIEventBus.post(new MainTabChangeEvent(TabId.OWNADS));
			return new OwnAdvertisementTableFrame();
		};
	};


	public TablesFrame() {
		TabSheet ts = new LazyTabSheet();
		ts.addStyleName(ValoTheme.TABSHEET_FRAMED);
		ts.addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
		Tab first = ts.addTab(tab1, "Feladatok");
		first.setIcon(FontAwesome.JOOMLA);
		Tab second = ts.addTab(tab2, "Hirdetéseim");
		second.setIcon(FontAwesome.AUTOMOBILE);
		setCompositionRoot(ts);
	}
}
