package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * The Class BackgroundPanel.
 *
 * @author MineRickStar
 */
public class BackgroundPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2238992011815695912L;

	/** The current background. */
	private Image currentBackground;

	/**
	 * Instantiates a new background panel.
	 *
	 * @param currentBackground the current background
	 */
	public BackgroundPanel(Image currentBackground) {
		this.currentBackground = currentBackground;
		this.setLayout(new GridLayout(10, 10));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.currentBackground, 0, 0, null);
	}
}