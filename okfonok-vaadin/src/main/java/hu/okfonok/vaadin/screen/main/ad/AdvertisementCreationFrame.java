package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.ad.JobCategory;
import hu.okfonok.common.Settlement;
import hu.okfonok.common.ValueSet;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.UIEventBus;
import hu.okfonok.vaadin.security.Authentication;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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
	private OptionGroup remunerationField;
	@PropertyId("maxOffer")
	private TextField maxOfferField;
	@PropertyId("homeJob")
	private CheckBox homeJobField;
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
		fg.setBuffered(false);
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setSpacing(true);
		root.setMargin(true);
		descriptionField = new TextArea();
		categoryField = new ComboBox("Kategória", JobCategory.findAll());
		addressSettlementField = new ComboBox("Település", Settlement.findAll());
		addressOtherField = new TextField("Cím");
		remunerationField = new OptionGroup("Díjazás", ValueSet.remuneration().getEntryValues());
		maxOfferField = new TextField("Maximum ajánlat");
		homeJobField = new CheckBox("Otthonról is végezhető");
		root.addComponent(categoryField);
		root.addComponent(descriptionField);
		root.addComponent(addressSettlementField);
		root.addComponent(addressOtherField);
		root.addComponent(homeJobField);
		root.addComponent(remunerationField);
		root.addComponent(maxOfferField);
		root.addComponent(createButton);
		fg.bindMemberFields(this);
		return root;
	}
}
