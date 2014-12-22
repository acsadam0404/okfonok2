package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.JobCategory;
import hu.okfonok.common.DateInterval;
import hu.okfonok.common.Settlement;
import hu.okfonok.common.ValueSet;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import java.util.List;

import by.kod.numberfield.NumberField;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;

public class AdvertisementCreationFrame extends CustomComponent {
	private OFFieldGroup<Advertisement> fg;

	@PropertyId("description")
	private TextArea descriptionField;
	@PropertyId("category")
	private ComboBox categoryField;
	@PropertyId("address.settlement")
	private ComboBox addressSettlementField;
	@PropertyId("address.other")
	private TextField addressOtherField;
	@PropertyId("remuneration")
	private ComboBox remunerationField;
	@PropertyId("maxOffer")
	private TextField maxOfferField;
	@PropertyId("homeJob")
	private CheckBox homeJobField;

	private ComboBox mainCategoryField;
	
	private Button createButton = new Button("Feladás", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				fg.commit();
				if (fg.isValid()) {
					fg.getBean().save();
					UIEventBus.post(new AdvertisementCreatedEvent());
				}
			} catch (CommitException e) {
				e.printStackTrace();
			}
		}
	});

	public AdvertisementCreationFrame() {
		setCaption("Hirdetés feladása");
		Advertisement ad = new Advertisement();
		ad.setUser(Authentication.getUser());
		fg = new OFFieldGroup<>(ad);
		setCompositionRoot(build());
	}

	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		hl.setSpacing(true);
		hl.addComponent(buildLeft());
		hl.addComponent(buildRight());
		
		return hl;
	}

	private Component buildLeft() {
		VerticalLayout root = new VerticalLayout();
		root.setSpacing(true);
		descriptionField = new TextArea("Feladat leírása");
		descriptionField.setInputPrompt("A feladat leírásban ne adj meg elérhetőségi adatokat!");
		descriptionField.setNullRepresentation("");
		descriptionField.setSizeFull();
		mainCategoryField = new ComboBox("Főkategória", JobCategory.findAllMain());
		categoryField = new ComboBox("Kategória", JobCategory.findAllSub());
		mainCategoryField.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				JobCategory mc = (JobCategory)event.getProperty().getValue();
				List<JobCategory> categories = null;
				if (mc != null) {
					categories = mc.getSubCategories();
				}
				else {
					categories = JobCategory.findAllSub();
				}
				categoryField.setContainerDataSource(new BeanItemContainer<>(JobCategory.class, categories));
			}
		});
		addressSettlementField = new ComboBox("Település", Settlement.findAll());
		addressOtherField = new TextField("Cím");
		remunerationField = new ComboBox("Díjazás", ValueSet.remuneration().getEntryValues());
		maxOfferField = new NumberField("Maximum ajánlat");
		maxOfferField.setNullRepresentation("");
		maxOfferField.setInputPrompt("Összeg forintban");
		homeJobField = new CheckBox("Otthonról is végezhető");

		HorizontalLayout c = new HorizontalLayout(mainCategoryField, categoryField);
		c.setSpacing(true);
		root.addComponent(c);
		root.addComponent(descriptionField);
		HorizontalLayout c2 = new HorizontalLayout(addressSettlementField, addressOtherField);
		c2.setSpacing(true);
		root.addComponent(c2);
		HorizontalLayout c3 = new HorizontalLayout(remunerationField, maxOfferField);
		c3.setSpacing(true);
		root.addComponent(c3);
		root.addComponent(homeJobField);
		root.addComponent(createButton);
		fg.bindMemberFields(this);
		return root;
	}
	
	
	private Component buildRight() {
		TabSheet ts = new TabSheet();
		Component calendar = buildCalendar();
		calendar.setWidth("600px");
		calendar.setHeight("600px");
		ts.addTab(calendar, "Naptár");
		CarouselFrame carouselFrame = new CarouselFrame();
		carouselFrame.setWidth("600px");
		carouselFrame.setHeight("600px");
		ts.addTab(carouselFrame, "Képek");
		return ts;
	}

	private Component buildCalendar() {
		final Calendar calendar = new Calendar();
		calendar.setWeeklyCaptionFormat("MMM dd");
		calendar.setHandler(new RangeSelectHandler() {
			
			@Override
			public void rangeSelect(RangeSelectEvent event) {
				DateInterval interval = new DateInterval(event.getStart(),event.getEnd());
				fg.getBean().getPreferredIntervals().add(interval);
				calendar.addEvent(new PreferredIntervalEvent(interval));
			}
		});
		
		calendar.setHandler(new EventClickHandler() {
			
			@Override
			public void eventClick(EventClick event) {
				PreferredIntervalEvent interval = (PreferredIntervalEvent)event.getCalendarEvent();
				fg.getBean().getPreferredIntervals().remove(interval.getDateInterval());
			}
		});
		
		calendar.setHandler((DateClickHandler)null);
		
		return calendar;
	}
}
