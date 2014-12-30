package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;
import hu.okfonok.offer.events.OfferCreatedEvent;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.math.BigDecimal;

import by.kod.numberfield.NumberField;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class PlaceOfferFrame extends CustomComponent {
	private Advertisement ad;
	private NumberField offerField;
	private PlaceOfferCalendar offerCalendar;

	private Button offerButton = new Button("Ajánlatot teszek", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				offerField.validate();
				BigDecimal amount = new BigDecimal(offerField.getValue());
				Offer offer = new Offer(Authentication.getUser(), ad, amount, offerCalendar.getIntervals());
				offer.save();
				UIEventBus.post(new OfferCreatedEvent(offer));
				UIEventBus.post(new Window.CloseEvent(PlaceOfferFrame.this));
			}
			catch (InvalidValueException ive) {
				ive.printStackTrace();
			}
		}
	});


	public PlaceOfferFrame(Advertisement ad) {
		assert ad != null;
		this.ad = ad;

		setCompositionRoot(build());
	}


	private Component build() {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);

		offerCalendar = new PlaceOfferCalendar(ad);
		l.addComponent(offerCalendar);

		HorizontalLayout offerLayout = new HorizontalLayout();
		offerLayout.setSizeFull();
		offerLayout.setMargin(true);
		offerField = new NumberField(null);
		offerField.setInputPrompt("Összeg");

		/* az ajánlat összege nem lehet nagyobb a hirdetés max összegénél */
		offerField.setMaxValue((double) ad.getMaxOffer());

		offerLayout.addComponent(offerField);
		Label currencyLabel = new Label("Forint");
		offerLayout.addComponent(currencyLabel);
		offerLayout.addComponent(offerButton);
		offerLayout.setComponentAlignment(offerField, Alignment.MIDDLE_LEFT);
		offerLayout.setComponentAlignment(currencyLabel, Alignment.MIDDLE_LEFT);
		offerLayout.setComponentAlignment(offerButton, Alignment.MIDDLE_RIGHT);
		l.addComponent(offerLayout);

		return l;
	}

}
