package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.user.Profile;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class ProfileViewFrame extends CustomComponent {
	private OFFieldGroup<User> fg;

	private TextField nameField;
	private TextField phoneNumberField;
	private TextField emailField;
	private TextField introductionField;

	private MessageBox messageBox;
	private ProfileImageFrame profileImageFrame;
	private ScoreFrame scoreFrame;


	public ProfileViewFrame(User user) {
		fg = new OFFieldGroup<>(user);
		fg.setReadOnly(true);
		messageBox = new MessageBox(user);
		profileImageFrame = new ProfileImageFrame(user);
		scoreFrame = new ScoreFrame(user);
		setCompositionRoot(build());
	}


	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(buildLeft());
		hl.addComponent(buildRight());
		fg.bind(nameField, "profile.name");
		fg.bind(phoneNumberField, "profile.phoneNumber");
		fg.bind(emailField, "profile.email");
		fg.bind(introductionField, "profile.introduction");

		return hl;
	}


	private Component buildLeft() {
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);
		FormLayout fl = new FormLayout();
		nameField = new TextField("Név");
		phoneNumberField = new TextField("Telefonszám");
		emailField = new TextField("E-mail cím");
		introductionField = new TextField("Bemutatkozás");
		fl.addComponent(nameField);
		fl.addComponent(phoneNumberField);
		fl.addComponent(emailField);
		l.addComponent(fl);
		l.addComponent(introductionField);
		l.addComponent(messageBox);
		return l;
	}


	private Component buildRight() {
		VerticalLayout l = new VerticalLayout();
		l.addComponent(profileImageFrame);
		l.addComponent(new Label("elvégzettfeladatokszáma label"));
		l.addComponent(scoreFrame);
		return l;
	}

}
