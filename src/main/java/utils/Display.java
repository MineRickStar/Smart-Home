package utils;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

/**
 * The Class Display.
 *
 * @author MineRickStar
 */
public final class Display {

	/**
	 * Instantiates a new display.
	 */
	private Display() {}

	public static GraphicsDevice getScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return env.getScreenDevices()[0];
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit()
				.getScreenSize();
	}

}
