package hu.okfonok;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;


public class ImageResizer {

	private int height;
	private Path path;
	private int width;


	public ImageResizer(Path path, int width, int height) {
		assert path != null;
		assert width > 0;
		assert height > 0;
		this.path = path;
		this.width = width;
		this.height = height;
	}


	private BufferedImage resize() throws IOException {
		BufferedImage image = ImageIO.read(path.toFile());
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}


	/**
	 * 
	 * @return sikeres volt-e az akció
	 */
	public boolean resizeAndSave() {
		return resizeAndSave(path);
	}


	public boolean resizeAndSave(Path whereToSave) {
		try {
			BufferedImage image = resize();
			ImageIO.write(image, "png", whereToSave.toFile());
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
