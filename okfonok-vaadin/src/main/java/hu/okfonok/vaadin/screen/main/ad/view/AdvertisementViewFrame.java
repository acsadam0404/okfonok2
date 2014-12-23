package hu.okfonok.vaadin.screen.main.ad.view;

import hu.okfonok.Config;
import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.JobCategory;
import hu.okfonok.ad.events.AdvertisementSaveEvent;
import hu.okfonok.user.Profile;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.component.DirectoryCarousel;
import hu.okfonok.vaadin.screen.profile.MessageBox;
import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class AdvertisementViewFrame extends CustomComponent {
	private OFFieldGroup<Advertisement> fg;
	private TabSheet tabsheet;


	private class OfferButton extends Button implements ClickListener {
		public OfferButton() {
			addClickListener(this);
			setCaption("Ajánlatot teszek");
			setIcon(FontAwesome.GAVEL);
		}


		@Override
		public void buttonClick(ClickEvent event) {
			//TODO ajánlat tabra váltást ne magic numberrel csináljuk
			tabsheet.setSelectedTab(5);
		}

	}


	private class BookmarkButton extends Button implements ClickListener {

		public BookmarkButton() {
			setCaption();
			addClickListener(this);
		}


		private void setCaption() {
			if (isSaved()) {
				setCaption("Törlöm");
				setIcon(FontAwesome.ERASER);
			}
			else {
				setCaption("Elmentem");
				setIcon(FontAwesome.STAR);
			}
		}


		private boolean isSaved() {
			return Authentication.getUser().isAdvertisementSaved(fg.getBean());
		}


		@Override
		public void buttonClick(ClickEvent event) {
			if (isSaved()) {
				Authentication.getUser().unsaveAdvertisement(fg.getBean());
				//TODO domain feladata a publish
				UIEventBus.post(new AdvertisementSaveEvent(false));
			}
			else {
				Authentication.getUser().saveAdvertisement(fg.getBean());
				//TODO domain feladata a publish
				UIEventBus.post(new AdvertisementSaveEvent(true));
			}
			setCaption();
		}
	};


	public AdvertisementViewFrame(Advertisement ad) {
		fg = new OFFieldGroup<>(ad);

		setCompositionRoot(build());
	}


	private Component build() {
		tabsheet = new TabSheet();
		tabsheet.setWidth("800px");
		tabsheet.setHeight("600px");

		tabsheet.addTab(buildData(), "Összefoglaló", FontAwesome.INFO);
		tabsheet.addTab(new DirectoryCarousel(Config.getAdRoot(Authentication.getUser(), fg.getBean().getUuid())), "Képek", FontAwesome.PICTURE_O);
		tabsheet.addTab(new AdvertisementViewMap(fg.getBean()), "Térkép", FontAwesome.MAP_MARKER);
		tabsheet.addTab(new AdvertisementViewCalendar(fg.getBean()), "Naptár", FontAwesome.CALENDAR);
		tabsheet.addTab(new MessageBox(fg.getBean()), "Üzenetek", FontAwesome.COMMENT);
		tabsheet.addTab(new OfferFrame(fg.getBean()), "Ajánlat", FontAwesome.GAVEL);

		return tabsheet;
	}


	private Component buildData() {
		HorizontalLayout l = new HorizontalLayout();
		l.setSizeFull();
		l.setSpacing(true);
		l.setMargin(true);

		VerticalLayout fl = new VerticalLayout();
		fl.setSizeFull();
		TextField nameField = new TextField("Hirdető");
		ComboBox mainCategoryField = new ComboBox("Főkategória", JobCategory.findAllMain());
		ComboBox categoryField = new ComboBox("Kategória", JobCategory.findAllSub());
		TextArea descriptionField = new TextArea("Hirdetés szövege");
		descriptionField.setSizeFull();
		TextField remunerationField = new TextField("Díjazás");
		TextField maxOfferField = new TextField("Maximum díj");
		fl.addComponent(nameField);
		fl.addComponent(mainCategoryField);
		fl.addComponent(categoryField);
		fl.addComponent(remunerationField);
		fl.addComponent(maxOfferField);
		Component actions = buildActions();
		fl.addComponent(actions);
		fl.setExpandRatio(actions, 1f);
		fl.setComponentAlignment(actions, Alignment.BOTTOM_LEFT);
		l.addComponent(fl);
		l.addComponent(descriptionField);

		fg.bind(nameField, "user.profile." + Profile.SHORTENEDNAME);
		fg.bind(mainCategoryField, Advertisement.MAINCATEGORY);
		fg.bind(categoryField, Advertisement.CATEGORY);
		fg.bind(descriptionField, Advertisement.DESCRIPTION);
		fg.bind(remunerationField, Advertisement.REMUNERATION);
		fg.bind(maxOfferField, "maxOffer");
		fg.setReadOnly(true);
		for (Field f : fg.getFields()) {
			f.setWidth("100%");
		}

		return l;
	}


	public Component buildActions() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(new OfferButton());
		hl.addComponent(new BookmarkButton());
		return hl;
	}

}
