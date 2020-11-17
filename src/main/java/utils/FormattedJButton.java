package utils;

import javax.swing.JButton;

/**
 * The Class FormattedJButton.
 *
 * @author MineRickStar
 */
public class FormattedJButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3657298547755110273L;

	/**
	 * Instantiates a new formattedJbutton.
	 *
	 * @param text the text
	 */
	public FormattedJButton(String text) {
		this.setText(text);
		this.setOpaque(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}

}