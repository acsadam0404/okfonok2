package hu.okfonok.vaadin.screen.main.ad;

import hu.okfonok.Config;
import hu.okfonok.ad.Advertisement;
import hu.okfonok.vaadin.security.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;


public class CarouselFrame extends CustomComponent implements UploadFinishedHandler {
	private HorizontalCarousel carousel;
	private MultiFileUpload upload;
	private Path imagePath;


	public CarouselFrame(Advertisement ad) {
		assert ad != null;
		carousel = buildCarousel();
		upload = buildUpload();
		imagePath = Config.getAdRoot(Authentication.getUser(), ad.getUuid());

		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		l.addComponent(carousel);
		l.addComponent(upload);
		l.setComponentAlignment(upload, Alignment.MIDDLE_CENTER);
		l.setExpandRatio(carousel, 1f);

		setCompositionRoot(l);
	}


	private MultiFileUpload buildUpload() {
		MultiFileUpload multiFileUpload = new MultiFileUpload(this, new UploadStateWindow());
		multiFileUpload.setSizeUndefined();
		multiFileUpload.setUploadButtonCaptions("Feltöltés", "Feltöltés");
		return multiFileUpload;
	}


	private HorizontalCarousel buildCarousel() {
		HorizontalCarousel carousel = new HorizontalCarousel();
		carousel.setSizeFull();
		carousel.setArrowKeysMode(ArrowKeysMode.FOCUS);
		carousel.setLoadMode(CarouselLoadMode.LAZY);
		carousel.setTransitionDuration(500);

		return carousel;
	}


	@Override
	public void handleFile(InputStream input, String fileName, String mimeType, long length) {

		try {
			if (!Files.exists(imagePath)) {
				Files.createDirectories(imagePath);
			}
			Files.copy(input, imagePath.resolve(fileName));
			refresh();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void refresh() {
		carousel.removeAllComponents();
		try {
			for (Path path : Files.newDirectoryStream(imagePath)) {
				Image image = new Image(path.toFile().getName(), new FileResource(path.toFile()));
				carousel.addComponent(image);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
