package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class AdvertisementViewImages extends CustomComponent {
	public AdvertisementViewImages(Advertisement ad) {
		setCompositionRoot(new Label("képek"));
		
	}
}
