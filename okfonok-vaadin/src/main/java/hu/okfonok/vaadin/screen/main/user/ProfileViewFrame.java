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
import com.vaadin.ui.VerticalLayout;


public class ProfileViewFrame extends CustomComponent {
	private OFFieldGroup<Profile> fg;

	@PropertyId("name")
	private Label nameField;
	@PropertyId("phoneNumber")
	private Label phoneNumberField;
	@PropertyId("email")
	private Label emailField;
	@PropertyId("introduction")
	private Label introductionField;

	private MessageBox messageBox;
	private ProfileImageFrame profileImageFrame;
	private ScoreFrame scoreFrame;


	public ProfileViewFrame(User user) {
		fg = new OFFieldGroup<>(user.getProfile());
		messageBox = new MessageBox(user);
		profileImageFrame = new ProfileImageFrame(user.getProfile());
		scoreFrame = new ScoreFrame(user);
		setCompositionRoot(build());
	}


	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(buildLeft());
		hl.addComponent(buildRight());
		fg.bindMemberFields(this);
		return hl;
	}


	private Component buildLeft() {
		VerticalLayout l = new VerticalLayout();
		FormLayout fl = new FormLayout();
		nameField = new Label("Név");
		phoneNumberField = new Label("Telefonszám");
		emailField = new Label("E-mail cím");
		introductionField = new Label("Bemutatkozás");
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
