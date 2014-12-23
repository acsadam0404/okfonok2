package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.math.BigDecimal;

import by.kod.numberfield.NumberField;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


public class OfferFrame extends CustomComponent {
	private Advertisement ad;
	private NumberField offerField = new NumberField(null);

	private Button offerButton = new Button("Ajánlatot teszek", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			BigDecimal amount = new BigDecimal(offerField.getValue());
			Offer offer = new Offer(Authentication.getUser(), ad, amount);
			offer.save();
			UIEventBus.post(new OfferCreatedEvent(offer));
		}
	});


	public OfferFrame(Advertisement ad) {
		assert ad != null;
		this.ad = ad;
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.setSpacing(true);
		AdvertisementViewCalendar calendar = new AdvertisementViewCalendar(ad);
		l.addComponent(calendar);
		HorizontalLayout offerLayout = new HorizontalLayout();
		offerLayout.setSpacing(true);
		offerLayout.setMargin(true);
		offerLayout.addComponent(offerField);
		offerLayout.addComponent(offerButton);
		l.addComponent(offerLayout);
		setCompositionRoot(l);
	}

}
