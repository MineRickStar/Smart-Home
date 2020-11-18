package gui;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.UIManager;
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

	/** The panel. */
	private BackgroundPanel panel;

	/**
	 * Instantiates a new smart home frame.
	 */
	public SmartHomeFrame() {
		Font font = new Font("Arial", Font.BOLD, 18);
		UIManager.getLookAndFeelDefaults()
				.keySet()
				.stream()
				.filter(o -> o.toString()
						.toLowerCase()
						.contains("font"))
				.forEach(o -> UIManager.getLookAndFeelDefaults()
						.put(o, font));
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
//		this.setUndecorated(true);
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