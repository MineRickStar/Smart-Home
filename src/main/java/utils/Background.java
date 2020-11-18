package utils;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * The Class Background.
 *
 * @author MineRickStar
 */
public class Background {

	/** The images. */
	private static final HashMap<String, Image> images = new HashMap<>();

	/**
	 * Instantiates a new background.
	 */
	private Background() {}

	/**
	 * Loads the Image with the given URL, if not found then {@code null} is
	 * returned.
	 *
	 * @param url the url
	 * @return the image
	 */
	public static Image load(String url) {
		if (Background.images.containsKey(url)) { return Background.images.get(url); }
		URL resource = Background.class.getClassLoader()
				.getResource(url);
		try {
			Image background = ImageIO.read(resource);
			Dimension d = Display.getScreenSize();
			background = background.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), Image.SCALE_REPLICATE);
			Background.images.put(url, background);
			return background;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}