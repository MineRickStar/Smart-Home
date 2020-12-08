package gui;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gui.BackgroundPanel.View;
import utils.Display;

/**
 * The Class SmartHomeFrame.
 *
 * @author MineRickStar
 */
public class SmartHomeFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4795265249200588064L;

	/** The Constant BLANK_CURSOR. */
	public static final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit()
			.createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
					"blank cursor");

	/** The panel. */
	private BackgroundPanel panel;

	// TODO debug false when ready
	/** The debug. */
	private final boolean debug = true;

	/**
	 * Instantiates a new smart home frame.
	 */
	public SmartHomeFrame() {
//		Font font = new Font("Arial", Font.BOLD, 18);
//		UIManager.getLookAndFeelDefaults()
//				.keySet()
//				.stream()
//				.filter(o -> o.toString()
//						.toLowerCase()
//						.contains("font"))
//				.forEach(o -> UIManager.getLookAndFeelDefaults()
//						.put(o, font));

		// Set the blank cursor to the JFrame.
		if (!this.debug) {
			this.setCursor(SmartHomeFrame.BLANK_CURSOR);
		}

		this.addBackgroundPanel();
		this.init();
		this.panel.changeView(View.DEFAULT);
	}

	/**
	 * Inits the Frame.
	 */
	private void init() {
		this.setTitle("Smart Home");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		if (!this.debug) {
			this.setUndecorated(true);
		}
		this.setAlwaysOnTop(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(Display.getScreenSize());
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Adds the background panel.
	 */
	private void addBackgroundPanel() {
		this.panel = new BackgroundPanel();
		this.setContentPane(this.panel);
	}

}