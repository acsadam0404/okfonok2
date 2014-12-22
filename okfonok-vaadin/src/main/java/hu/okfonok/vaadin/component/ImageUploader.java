package hu.okfonok.vaadin.component;

import hu.okfonok.ImageResizer;

import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.CustomComponent;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadFinishedHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;


public class ImageUploader extends CustomComponent {
	private List<FileUploadListener> fileUploadListeners = new ArrayList<>();

	private int targetWidth = 200;
	private int targetHeight = 200;

	private MultiFileUpload upload;

	public ImageUploader(final Path path) {
		assert path != null;
		upload = new MultiFileUpload(new UploadFinishedHandler() {

			@Override
			public void handleFile(InputStream input, String fileName, String mimeType, long length) {

				try {
					if (!Files.exists(path)) {
						Files.createDirectories(path);
					}
					Files.copy(input, path.resolve(fileName));
					new ImageResizer(path.resolve(fileName), targetWidth, targetHeight).resizeAndSave();
					for (FileUploadListener fileUploadListener : fileUploadListeners) {
						fileUploadListener.success(fileName);
					}
				}
				catch (FileAlreadyExistsException faee) {
					faee.printStackTrace();
					for (FileUploadListener fileUploadListener : fileUploadListeners) {
						fileUploadListener.failure(fileName, FileUploadListener.Reason.AlreadyExists);
					}

				}
				catch (Exception ioEx) {
					ioEx.printStackTrace();
					for (FileUploadListener fileUploadListener : fileUploadListeners) {
						fileUploadListener.failure(fileName, FileUploadListener.Reason.General);
					}
				}
			}
		}, new UploadStateWindow(), true);

		setCompositionRoot(upload);
	}


	public void setTargetWidth(int targetWidth) {
		this.targetWidth = targetWidth;
	}


	public void setUploadButtonCaptions(String first, String second) {
		upload.setUploadButtonCaptions(first, second);
	}


	public void setTargetHeight(int targetHeight) {
		this.targetHeight = targetHeight;
	}


	public void addFileUploadListener(FileUploadListener fileUploadListener) {
		fileUploadListeners.add(fileUploadListener);
	}


	public static interface FileUploadListener {
		public static enum Reason {
			AlreadyExists("already exists"),
			General("general");

			private String text;


			private Reason(String text) {
				this.text = text;
			}


			public String getText() {
				return text;
			}
		}


		public void success(String fileName);


		public void failure(String fileName, Reason alreadyexists);
	}

}
