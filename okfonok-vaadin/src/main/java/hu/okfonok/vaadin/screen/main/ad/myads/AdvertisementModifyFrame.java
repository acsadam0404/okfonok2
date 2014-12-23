package hu.okfonok.vaadin.screen.main.ad.myads;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.OFFieldGroup;


public class AdvertisementModifyFrame extends CustomComponent {

	private OFFieldGroup<Advertisement> fg;

	public AdvertisementModifyFrame(Advertisement ad) {
		fg = new OFFieldGroup<>(ad);
		setCompositionRoot(build());
	}


	private Component build() {
		return new Label("itt lehetne módosítani az ajánlatot: vagy ne lehessen vagy csak addig amíg nem érkezik rá offer, mert vissza lehet élni vele");
	}
}
