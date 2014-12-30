package hu.okfonok.vaadin.screen.profile;

import hu.okfonok.ImageResizer;
import hu.okfonok.user.User;
import hu.okfonok.user.events.ProfileImageUpdatedEvent;
import hu.okfonok.vaadin.MimeType;
import hu.okfonok.vaadin.UIEventBus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;


public class ProfileImageUpload extends MultiFileUpload {
	ProfileImageUpload(final User user) {
		super(new UploadFinishedHandler() {

			@Override
			public void handleFile(InputStream input, String fileName, String mimeType, long length) {
				try {
					Path profileRoot = uploadOriginal(user, input);
					createProfileImages(user, profileRoot);
					UIEventBus.post(new ProfileImageUpdatedEvent(user));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}


			private Path uploadOriginal(final User user, InputStream input) throws IOException {
				Path profileRoot = user.getProfileImageOriginalPath();
				Files.deleteIfExists(profileRoot);
				if (!Files.exists(profileRoot.getParent())) {
					Files.createDirectories(profileRoot.getParent());
				}
				Files.copy(input, profileRoot);
				return profileRoot;
			}


			private void createProfileImages(final User user, Path profileRoot) {
				new ImageResizer(profileRoot, 100, 100).resizeAndSave(user.getProfileImageLargePath());
				new ImageResizer(profileRoot, 50, 50).resizeAndSave(user.getProfileImageSmallPath());
			}

		}, new UploadStateWindow());

		setAcceptedMimeTypes(MimeType.getImageTypes());
	}
}