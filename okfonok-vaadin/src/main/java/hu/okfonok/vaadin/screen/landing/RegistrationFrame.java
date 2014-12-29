package hu.okfonok.vaadin.screen.landing;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;
import by.kod.numberfield.NumberField;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
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
		nameField.setIcon(FontAwesome.DRUPAL);
		nameField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		nameField.setNullRepresentation("");
		emailField = new TextField();
		emailField.setInputPrompt("E-mail cím");
		emailField.setIcon(FontAwesome.ENVELOPE);
		emailField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		emailField.setNullRepresentation("");
		passwordField = new PasswordField();
		passwordField.setInputPrompt("Jelszó");
		passwordField.setNullRepresentation("");
		passwordField.setIcon(FontAwesome.KEY);
		passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		zipcodeField = new NumberField();
		zipcodeField.setInputPrompt("Irányítószám");
		zipcodeField.setNullRepresentation("");
		zipcodeField.setIcon(FontAwesome.ARROW_CIRCLE_LEFT);
		zipcodeField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		readTermsField = new CheckBox("Elolvastam és elfogadom az Adatkezelést és a Felhasználási feltételeket");

		l.addComponents(nameField, emailField, passwordField, zipcodeField, regButton, readTermsField, facebookRegButton);
		fg.bind(zipcodeField, "address.zipcode");
		fg.bind(nameField, "profile.name");
		fg.bind(emailField, "profile.email");
		fg.bindMemberFields(this);
		return l;
	}
}
