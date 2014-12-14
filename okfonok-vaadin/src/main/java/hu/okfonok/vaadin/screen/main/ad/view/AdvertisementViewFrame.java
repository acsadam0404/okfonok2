package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.offer.Offer;
import hu.okfonok.user.Profile;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.screen.main.user.MessageBox;
import hu.okfonok.vaadin.security.Authentication;

import java.math.BigDecimal;

import by.kod.numberfield.NumberField;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdvertisementViewFrame extends CustomComponent {
	private OFFieldGroup<Advertisement> fg;
	
	private NumberField offerField = new NumberField("Ajánlatom");
	
	private Button saveButton = new Button("Elmentem", new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	});
	
	private Button offerButton = new Button("Ajánlatom", new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			BigDecimal amount = new BigDecimal(offerField.getValue());
			Offer offer = new Offer(Authentication.getUser(), fg.getBean(), amount);
			offer.save();
			UIEventBus.post(new OfferCreatedEvent(offer));
		}
	});

	public AdvertisementViewFrame(Advertisement ad) {
		fg = new OFFieldGroup<>(ad);

		setCompositionRoot(build());
	}

	private Component build() {
		TabSheet l = new TabSheet();
		l.setWidth("800px");
		l.setHeight("600px");
		
		l.addTab(buildData(), "Összefoglaló");
		l.addTab(new AdvertisementViewImages(fg.getBean()), "Képek");
		l.addTab(new AdvertisementViewMap(fg.getBean()), "Térkép");
		l.addTab(new AdvertisementViewCalendar(fg.getBean()), "Naptár");
		l.addTab(new MessageBox(fg.getBean()), "Üzenetek");
		
		return l;
	}

	private Component buildData() {
		HorizontalLayout l = new HorizontalLayout();
		l.setSizeFull();
		l.setMargin(true);
		
		VerticalLayout fl = new VerticalLayout();
		TextField nameField = new TextField("Hirdető");
		ComboBox mainCategoryField = new ComboBox("Főkategória");
		ComboBox categoryField = new ComboBox("Kategória");
		TextArea descriptionField = new TextArea("Hirdetés szövege");
		descriptionField.setSizeFull();
		TextField remunerationField = new TextField("Díjazás");
		TextField maxOfferField = new TextField("Maximum díj");
		fl.addComponent(nameField);
		fl.addComponent(mainCategoryField);
		fl.addComponent(categoryField);
		fl.addComponent(remunerationField);
		fl.addComponent(maxOfferField);
		fl.addComponent(saveButton);
		l.addComponent(offerField);
		fl.addComponent(offerButton);
		l.addComponent(fl);
		l.addComponent(descriptionField);

		fg.bind(nameField, "user.profile." + Profile.SHORTENEDNAME);
		fg.bind(mainCategoryField, Advertisement.MAINCATEGORY);
		fg.bind(categoryField, Advertisement.CATEGORY);
		fg.bind(descriptionField, Advertisement.DESCRIPTION);
		fg.bind(remunerationField, Advertisement.REMUNERATION);
		fg.bind(maxOfferField, "maxOffer");
		fg.setReadOnly(true);

		return l;
	}

}
