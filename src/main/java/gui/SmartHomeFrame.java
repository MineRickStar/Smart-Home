package gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import utils.Display;

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

	/**
	 * Instantiates a new smart home frame.
	 */
	public SmartHomeFrame() {
		this.loadResources();
		this.init();
		this.addBackgroundPanel();
	}

	/**
	 * Inits the Frame.
	 */
	private void init() {
		this.setTitle("Smart Home");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
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
		this.add(new JPanel() {
			private static final long serialVersionUID = -8288254215282845925L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(SmartHomeFrame.this.background, 0, 0, null);
			}
		});
	}

}