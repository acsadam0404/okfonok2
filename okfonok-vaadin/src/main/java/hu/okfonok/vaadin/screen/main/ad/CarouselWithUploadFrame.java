package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.Config;
import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.component.DirectoryCarousel;
import hu.okfonok.vaadin.component.ImageUploader;
import hu.okfonok.vaadin.component.ImageUploader.FileUploadListener;
import hu.okfonok.vaadin.security.Authentication;

import java.nio.file.Path;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;


public class CarouselWithUploadFrame extends CustomComponent {
	private Path imagePath;
	private DirectoryCarousel carousel;


	public CarouselWithUploadFrame(Advertisement ad) {
		assert ad != null;
		imagePath = Config.getAdRoot(Authentication.getUser(), ad.getUuid());

		Component upload = buildUpload();
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		carousel = new DirectoryCarousel(imagePath);
		l.addComponent(carousel);
		l.addComponent(upload);
		l.setComponentAlignment(upload, Alignment.MIDDLE_CENTER);
		l.setExpandRatio(carousel, 1f);

		setCompositionRoot(l);
	}


	private Component buildUpload() {
		ImageUploader multiFileUpload = new ImageUploader(imagePath);
		multiFileUpload.setUploadButtonCaptions("Feltöltés", "Feltöltés");
		multiFileUpload.addFileUploadListener(new FileUploadListener() {

			@Override
			public void success(String fileName) {
				carousel.refresh();
			}


			@Override
			public void failure(String fileName, Reason alreadyexists) {

			}

		});
		return multiFileUpload;
	}

}
