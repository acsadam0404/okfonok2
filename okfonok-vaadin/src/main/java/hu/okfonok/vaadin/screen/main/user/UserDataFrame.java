package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class UserDataFrame extends CustomComponent {
	private OFFieldGroup<User> fg;

	private TextArea introductionField;
	private TextField emailField;
	private TextField phoneNumberField;
	private TextField idCardField;
	private TextField addressCardField;

	private Button editButton = new Button("Szerkesztés", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			if (!fg.isReadOnly()) {
				try {
					fg.commit();
					if (fg.isValid()) {
						fg.getBean().save();
						fg.setReadOnly(!fg.isReadOnly());
					}
				}
				catch (CommitException e) {}
			}
			else {
				fg.setReadOnly(!fg.isReadOnly());
			}
			editButton.setCaption(fg.isReadOnly() ? "Szerkesztés" : "Mentés");
		}
	});


	public UserDataFrame(User user) {
		fg = new OFFieldGroup<>(user);
		fg.setReadOnly(true);
		setCompositionRoot(build());
	}


	private Component build() {
		introductionField = new TextArea("Magamról");
		introductionField.setNullRepresentation("");
		
		emailField = new TextField();
		emailField.setIcon(FontAwesome.ANCHOR);
		emailField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		emailField.setNullRepresentation("");
		emailField.setInputPrompt("E-mail cím");
		
		phoneNumberField = new TextField();
		phoneNumberField.setIcon(FontAwesome.ANCHOR);
		phoneNumberField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		phoneNumberField.setNullRepresentation("");
		phoneNumberField.setInputPrompt("Telefonszám");
		
		idCardField = new TextField();
		idCardField.setNullRepresentation("");
		idCardField.setIcon(FontAwesome.ANCHOR);
		idCardField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		
		addressCardField = new TextField();
		addressCardField.setNullRepresentation("");
		addressCardField.setIcon(FontAwesome.ANCHOR);
		addressCardField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		
		
		Label onlyOnConnectionLabel = new Label("Csak létrejött kapcsolat esetén kerül továbbításra");
		Label legalizationLabel = new Label("Hitelesítéshez szükséges adatok");
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		l.addComponents(emailField, phoneNumberField, onlyOnConnectionLabel, legalizationLabel, idCardField, addressCardField, introductionField, editButton);

		fg.bind(emailField, "profile.email");
		fg.bind(introductionField, "profile.introduction");
		fg.bind(phoneNumberField, "profile.phoneNumber");
		fg.bind(idCardField, "profile.idCard");
		fg.bind(addressCardField, "profile.addressCard");

		Panel panel = new Panel(l);
		panel.setCaption("Alapadatok");
		return panel;
	}

}
