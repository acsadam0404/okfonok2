package hu.okfonok.vaadin.component;

import hu.okfonok.vaadin.component.ImageUploader.FileUploadListener;

import java.nio.file.Path;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;


public class CarouselWithUploadFrame extends CustomComponent {
	private Path imagePath;
	private DirectoryCarousel carousel;
	private ImageUploader upload;


	public void addFileUploadListener(FileUploadListener fileUploadListener) {
		upload.addFileUploadListener(fileUploadListener);
	}


	public CarouselWithUploadFrame(Path imagePath) {
		this.imagePath = imagePath;

		upload = buildUpload();
		VerticalLayout l = new VerticalLayout();
		l.setSizeFull();
		carousel = new DirectoryCarousel(imagePath);
		l.addComponent(carousel);
		l.addComponent(upload);
		l.setComponentAlignment(upload, Alignment.MIDDLE_CENTER);
		l.setExpandRatio(carousel, 1f);

		setCompositionRoot(l);
	}


	private ImageUploader buildUpload() {
		ImageUploader multiFileUpload = new ImageUploader(imagePath);
		multiFileUpload.setUploadButtonCaptions("Feltöltés", "Feltöltés");
		multiFileUpload.addFileUploadListener(new FileUploadListener() {

			@Override
			public void success(Path filePath) {
				carousel.refresh();
			}


			@Override
			public void failure(Path filePath, Reason alreadyexists) {

			}

		});
		return multiFileUpload;
	}

}
