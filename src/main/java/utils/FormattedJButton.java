package utils;

import java.awt.Color;

import javax.swing.JButton;

/**
 * The Class FormattedJButton.
 *
 * @author MineRickStar
 */
public class FormattedJButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3657298547755110273L;

	/** The Constant selectedColor. */
	public static final Color selectedColor = Color.GREEN;

	/** The Constant notSelectedColor. */
	public static final Color notSelectedColor = Color.BLUE;

	/** The selected. */
	private boolean selected;

	/**
	 * Instantiates a new formattedJbutton.
	 *
	 * @param text     the text
	 * @param selected the selected
	 */
	public FormattedJButton(String text, boolean selected) {
		this.setText(text);
//		this.setOpaque(false);
//		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setSelectedColor(selected);
	}

	public void setSelectedColor(boolean selected) {
		this.selected = selected;
		this.setBackground(selected ? FormattedJButton.selectedColor : FormattedJButton.notSelectedColor);
	}

	public boolean isSelectedColor() {
		return this.selected;
	}
}