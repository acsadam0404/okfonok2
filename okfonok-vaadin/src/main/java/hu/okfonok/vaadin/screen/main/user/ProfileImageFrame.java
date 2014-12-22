package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.Config;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.security.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;


public class ProfileImageFrame extends CustomComponent {
	private User user;
	private Path profileRoot = Config.getUserRoot(Authentication.getUser()).resolve("profile");
	/**
	 * ezen van rajta az upload gomb és a profilkép
	 */
	private VerticalLayout imageHolder;
	private MultiFileUpload upload = new MultiFileUpload(new UploadFinishedHandler() {

		@Override
		public void handleFile(InputStream input, String fileName, String mimeType, long length) {
			try {
				if (!Files.exists(profileRoot)) {
					Files.createDirectories(profileRoot);
				}
				Files.copy(input, profileRoot);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}, new UploadStateWindow());



	public ProfileImageFrame(User user) {
		this.user = user;
		setCompositionRoot(build());
		refresh();
	}


	private Component build() {
		VerticalLayout l = new VerticalLayout();
		l.addComponent(new SkillCircle(user));
		l.addComponent(new Label(user.getProfile().getName()));
		l.addComponent(new Label("/" + user.getRatings().size()));
		imageHolder = new VerticalLayout();
		l.addComponent(imageHolder);
		RatingStars stars = new RatingStars();
		stars.setValue(user.getRating());
		stars.setReadOnly(true);
		l.addComponent(stars);
		return l;
	}


	private void refresh() {
		imageHolder.removeAllComponents();
		/* itt mindig van kép, hiszen amikor létrehozzuk a usert adunk egy default képet neki */
		if (!Files.exists(profileRoot)) {
			createDefaultProfileImage();
		}
		Image profileImage = new Image(profileRoot.toFile().getName(), new FileResource(profileRoot.toFile()));
		imageHolder.addComponent(profileImage);
		imageHolder.addComponent(upload);
	}


	/**
	 * "profile" néven kell létrehoznunk a user fő könyvtárában
	 */
	private void createDefaultProfileImage() {
		try {
			Files.createDirectories(profileRoot.getParent());
			Files.copy(Config.getAppRoot().resolve("defaultprofileimage"), profileRoot);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
