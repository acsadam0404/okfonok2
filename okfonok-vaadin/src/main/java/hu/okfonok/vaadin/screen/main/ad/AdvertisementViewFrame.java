package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.screen.main.user.MessageBox;
import hu.okfonok.vaadin.screen.main.user.ProfileImageFrame;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;


public class AdvertisementViewFrame extends CustomComponent {
	private OFFieldGroup<Advertisement> fg;


	public AdvertisementViewFrame(Advertisement ad) {
		fg = new OFFieldGroup<>(ad);
	}

}
