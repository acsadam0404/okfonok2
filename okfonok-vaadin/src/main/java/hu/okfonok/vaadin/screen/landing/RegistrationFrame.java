package hu.okfonok.vaadin.screen.landing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import hu.okfonok.Config;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.security.Authentication;
import by.kod.numberfield.NumberField;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class RegistrationFrame extends CustomComponent {
	private OFFieldGroup<User> fg = new OFFieldGroup<>(new User());

	@PropertyId("profile.name")
	private TextField nameField;
	@PropertyId("profile.email")
	private TextField emailField;
	@PropertyId("password")
	private PasswordField passwordField;
	@PropertyId("address.zipcode")
	private NumberField zipcodeField;

	private CheckBox readTermsField;

	private Button regButton = new Button("Regisztráció", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				fg.commit();
				if (fg.isValid()) {
					fg.getBean().register();
				}
			}
			catch (CommitException e) {
				e.printStackTrace();
			}
		}
	});

	private Button facebookRegButton = new Button("Regisztráció Facebookkal", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub

		}
	});


	public RegistrationFrame() {
		setCompositionRoot(build());
	}


	private Component build() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);

		nameField = new TextField();
		nameField.setInputPrompt("Teljes Név");
		nameField.setNullRepresentation("");
		emailField = new TextField();
		emailField.setInputPrompt("E-mail cím");
		emailField.setNullRepresentation("");
		passwordField = new PasswordField();
		passwordField.setInputPrompt("Jelszó");
		passwordField.setNullRepresentation("");
		zipcodeField = new NumberField();
		zipcodeField.setInputPrompt("Irányítószám");
		zipcodeField.setNullRepresentation("");

		readTermsField = new CheckBox("Elolvastam és elfogadom az Adatkezelést és a Felhasználási feltételeket");

		l.addComponents(nameField, emailField, passwordField, zipcodeField, regButton, readTermsField, facebookRegButton);
		fg.bind(zipcodeField, "address.zipcode");
		fg.bind(nameField, "profile.name");
		fg.bind(emailField, "profile.email");
		fg.bindMemberFields(this);
		return l;
	}
}
