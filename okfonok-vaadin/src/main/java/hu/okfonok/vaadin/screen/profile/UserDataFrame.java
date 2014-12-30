package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.Config;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.OFFieldGroup;
import hu.okfonok.vaadin.security.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;


public class UserDataFrame extends CustomComponent {
	private OFFieldGroup<User> fg;

	private TextArea introductionField;
	private TextField emailField;
	private TextField phoneNumberField;
	private TextField idCardField;
	private TextField addressCardField;

	private MultiFileUpload uploadAddressCard = new MultiFileUpload(new UploadFinishedHandler() {
		@Override
		public void handleFile(InputStream input, String fileName, String mimeType, long length) {
			Path userRoot = Config.getUserRoot(Authentication.getUser());
			try {
				if (!Files.exists(userRoot)) {
					Files.createDirectories(userRoot);
				}
				Files.copy(input, userRoot.resolve("addresscard"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}, new UploadStateWindow());

	private MultiFileUpload uploadIdCard = new MultiFileUpload(new UploadFinishedHandler() {

		@Override
		public void handleFile(InputStream input, String fileName, String mimeType, long length) {
			Path userRoot = Config.getUserRoot(Authentication.getUser());
			try {
				if (!Files.exists(userRoot)) {
					Files.createDirectories(userRoot);
				}
				Files.copy(input, userRoot.resolve("idcard"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}, new UploadStateWindow());

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
		
		emailField = new TextField("E-mail cím");
		emailField.setIcon(FontAwesome.ENVELOPE);
		emailField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		emailField.setNullRepresentation("");
		emailField.setInputPrompt("E-mail cím");
		
		phoneNumberField = new TextField("Telefonszám");
		phoneNumberField.setIcon(FontAwesome.PHONE);
		phoneNumberField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		phoneNumberField.setNullRepresentation("");
		phoneNumberField.setInputPrompt("Telefonszám");
		
		HorizontalLayout idl = new HorizontalLayout();
		idl.setSpacing(true);
		idCardField = new TextField();
		idCardField.setNullRepresentation("");
		idCardField.setIcon(FontAwesome.USER);
		idCardField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		idl.addComponent(idCardField);
		idl.addComponent(uploadIdCard);
		
		HorizontalLayout addressl = new HorizontalLayout();
		addressl.setSpacing(true);
		addressCardField = new TextField();
		addressCardField.setNullRepresentation("");
		addressCardField.setIcon(FontAwesome.HOME);
		addressCardField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		addressl.addComponent(addressCardField);
		addressl.addComponent(uploadAddressCard);
		
		
		Label onlyOnConnectionLabel = new Label("Csak létrejött kapcsolat esetén kerül továbbításra");
		Label legalizationLabel = new Label("Hitelesítéshez szükséges adatok");
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		l.addComponents(emailField, phoneNumberField, onlyOnConnectionLabel, legalizationLabel, idl, addressl, introductionField, editButton);

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
