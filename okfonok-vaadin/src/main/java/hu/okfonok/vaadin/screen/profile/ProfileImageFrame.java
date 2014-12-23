package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.Config;
import hu.okfonok.ImageResizer;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.MimeType;
import hu.okfonok.vaadin.security.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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


	/*TODO külön kellene az upload, mert ezt a classt használja a megtekintés is 
	 */
	private class ProfileImageUpload extends MultiFileUpload {
		private ProfileImageUpload() {
			super(new UploadFinishedHandler() {

				@Override
				public void handleFile(InputStream input, String fileName, String mimeType, long length) {
					try {
						Files.deleteIfExists(profileRoot);
						if (!Files.exists(profileRoot.getParent())) {
							Files.createDirectories(profileRoot.getParent());
						}
						Files.copy(input, profileRoot);
						new ImageResizer(profileRoot, 100, 100).resizeAndSave();
						refresh();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}, new UploadStateWindow());

			setAcceptedMimeTypes(MimeType.getImageTypes());
		}
	}


	public ProfileImageFrame(User user) {
		this.user = user;
		setCompositionRoot(build());
		refresh();
	}


	private Component build() {
		//TODO ezt úgy kéne megcsinálni, hogy a skillkör közeépre menjen az imageholder
		VerticalLayout l = new VerticalLayout();
		l.addComponent(new SkillCircle(user));
		l.addComponent(new Label(user.getProfile().getName()));
		l.addComponent(new Label("/" + user.getRatings().size()));
		imageHolder = new VerticalLayout();
		l.addComponent(imageHolder);
		l.addComponent(new Rating(user));
		return l;
	}


	private void refresh() {
		imageHolder.removeAllComponents();
		if (!Files.exists(profileRoot)) {
			createDefaultProfileImage();
		}
		Image profileImage = new Image(profileRoot.toFile().getName(), new FileResource(profileRoot.toFile()));
		imageHolder.addComponent(profileImage);
		imageHolder.addComponent(new ProfileImageUpload());
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
