package hu.okfonok.vaadin.screen.main;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.screen.main.user.ProfileImageFrame;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


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
		emailField = new TextField();
		phoneNumberField = new TextField();
		idCardField = new TextField();
		addressCardField = new TextField();
		VerticalLayout l = new VerticalLayout();
		ProfileImageFrame profileImageFrame = new ProfileImageFrame(fg.getBean());
		l.addComponents(emailField, phoneNumberField, idCardField, addressCardField, profileImageFrame, introductionField, editButton);

		fg.bind(emailField, "profile.email");
		fg.bind(introductionField, "profile.introduction");
		fg.bind(phoneNumberField, "profile.phoneNumber");
		fg.bind(idCardField, "profile.idCard");
		fg.bind(addressCardField, "profile.addressCard");

		return l;
	}

}
