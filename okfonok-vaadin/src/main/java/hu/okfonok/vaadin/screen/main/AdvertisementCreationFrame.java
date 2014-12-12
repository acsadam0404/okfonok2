package hu.okfonok.vaadin.screen.main;

import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.OFFieldGroup;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class AdvertisementCreationFrame extends CustomComponent {
	private OFFieldGroup<Advertisement> fg;

	@PropertyId("description")
	private TextField descriptionField;

	private Button createButton = new Button("Létrehozás", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			if (fg.isValid()) {
				fg.getBean().save();
			}
		}
	});


	public AdvertisementCreationFrame() {
		setCaption("Hirdetés feladása");
		fg = new OFFieldGroup<>(new Advertisement());
		setCompositionRoot(build());
	}


	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setSpacing(true);
		root.setMargin(true);
		descriptionField = new TextField();
		root.addComponent(descriptionField);
		root.addComponent(createButton);
		fg.bindMemberFields(this);
		return root;
	}
}
