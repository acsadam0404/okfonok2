package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


public class ProfileViewFrame extends CustomComponent {
	private OFFieldGroup<User> fg;

	private TextField nameField;
	private TextField phoneNumberField;
	private TextField emailField;
	private TextArea introductionField;

	private ProfileImageFrame profileImageFrame;
	private ScoreFrame scoreFrame;


	public ProfileViewFrame(User user) {
		fg = new OFFieldGroup<>(user);
		fg.setReadOnly(true);
		setHeight("600px");
		profileImageFrame = new ProfileImageFrame(user);
		profileImageFrame.setWidth("400px");
		profileImageFrame.setHeight("400px");
		scoreFrame = new ScoreFrame(user);
		setCompositionRoot(build());
	}


	private Component build() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.addComponent(buildLeft());
		hl.addComponent(buildRight());
		fg.bind(nameField, "profile.name");
		fg.bind(phoneNumberField, "profile.phoneNumber");
		fg.bind(emailField, "profile.email");
		fg.bind(introductionField, "profile.introduction");

		for (Field f : fg.getFields()) {
			f.setWidth("100%");
		}
		return hl;
	}


	private Component buildLeft() {
		VerticalLayout fl = new VerticalLayout();
		fl.setMargin(true);
		fl.setSpacing(true);
		fl.setWidth("400px");
		fl.setHeight("100%");
		nameField = new TextField("Név");
		phoneNumberField = new TextField("Telefonszám");
		phoneNumberField.setNullRepresentation("");
		emailField = new TextField("E-mail cím");
		introductionField = new TextArea("Bemutatkozás");
		introductionField.setSizeFull();
		introductionField.setNullRepresentation("");
		fl.addComponent(nameField);
		fl.addComponent(phoneNumberField);
		fl.addComponent(emailField);
		fl.addComponent(introductionField);
		fl.setExpandRatio(introductionField, 1f);
		return fl;
	}


	private Component buildRight() {
		VerticalLayout l = new VerticalLayout();
		l.addComponent(profileImageFrame);
		l.addComponent(new Label("elvégzettfeladatokszáma label"));
		l.addComponent(scoreFrame);
		return l;
	}

}
