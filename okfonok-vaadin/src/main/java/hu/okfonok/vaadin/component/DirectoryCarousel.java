package hu.okfonok.vaadin.component;

import hu.okfonok.vaadin.MimeType;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.vaadin.virkki.carousel.HorizontalCarousel;
import org.vaadin.virkki.carousel.client.widget.gwt.ArrowKeysMode;
import org.vaadin.virkki.carousel.client.widget.gwt.CarouselLoadMode;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


/**
 * egy megadott root directoryból csinál carouselt
 * 
 * @author aacs
 * 
 */
public class DirectoryCarousel extends HorizontalCarousel {

	private Path root;
	private Filter<Path> imageFilter = new Filter<Path>() {

		@Override
		public boolean accept(Path entry) throws IOException {
			/* rejtett fájlokat . jelöli */
			if (entry.toFile().getName().startsWith(".")) {
				return false;
			}
			/* csak képeket mutatunk */
			String mimeType = Files.probeContentType(entry);
			if (MimeType.getImageTypes().contains(mimeType)) {
				return true;
			}
			return false;
		}
	};


	public DirectoryCarousel(Path root) {
		this.root = root;
		setSizeFull();
		setArrowKeysMode(ArrowKeysMode.FOCUS);
		setLoadMode(CarouselLoadMode.LAZY);
		setTransitionDuration(500);
		refresh();
	}


	public void refresh() {
		removeAllComponents();
		if (!Files.exists(root)) {
			setArrowKeysMode(ArrowKeysMode.DISABLED);
			setButtonsVisible(false);

			Label label = new Label("Nincs megjeleníthető kép");
			label.setSizeUndefined();
			label.setStyleName(ValoTheme.LABEL_HUGE);
			VerticalLayout layout = new VerticalLayout(label);
			layout.setSizeFull();
			layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			addComponent(layout);
		}
		else {
			try {

				for (Path path : Files.newDirectoryStream(root, imageFilter)) {
					Image image = new Image(path.toFile().getName(), new FileResource(path.toFile()));
					VerticalLayout layout = new VerticalLayout(image);
					layout.setSizeFull();
					layout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
					addComponent(layout);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
