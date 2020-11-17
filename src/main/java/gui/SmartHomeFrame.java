package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import utils.Display;
import utils.FormattedJButton;

/**
 * The Class SmartHomeFrame.
 *
 * @author MineRickStar
 */
public class SmartHomeFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4795265249200588064L;

	/** The background. */
	private Image background;

	/** The panel. */
	private BackgroundPanel panel;

	/**
	 * Instantiates a new smart home frame.
	 */
	public SmartHomeFrame() {
		this.loadResources();
		this.addBackgroundPanel();
		this.init();
	}

	/**
	 * Inits the Frame.
	 */
	private void init() {
		this.setTitle("Smart Home");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(Display.getScreenSize());
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Loads all resources.
	 */
	private void loadResources() {
		URL resource = this.getClass()
				.getClassLoader()
				.getResource("Black screen.png");
		try {
			this.background = ImageIO.read(resource);
			Dimension d = Display.getScreenSize();
			this.background = this.background.getScaledInstance((int) d.getWidth(), (int) d.getHeight(),
					Image.SCALE_REPLICATE);
			this.revalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the background panel.
	 */
	private void addBackgroundPanel() {
		this.panel = new BackgroundPanel(this.background);
		JButton button = new FormattedJButton("Testbutton");
		button.setForeground(Color.WHITE);
		button.addActionListener(e -> System.out.println(e));
		this.panel.add(button);
		this.setContentPane(this.panel);
	}
}