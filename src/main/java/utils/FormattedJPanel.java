package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The Class FormattedJButton.
 *
 * @author MineRickStar
 */
public class FormattedJPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3657298547755110273L;

	/** The Constant selectedColor. */
	public static final Color selectedColor = Color.GREEN;

	/** The Constant notSelectedColor. */
	public static final Color notSelectedColor = Color.BLUE;

	private final FormattedButton button;

	/**
	 * Instantiates a new formattedJbutton.
	 *
	 * @param text     the text
	 * @param selected the selected
	 */
	public FormattedJPanel(String text, boolean selected, Dimension dim) {
		this.setLayout(new GridLayout(1, 1, 0, 0));
		this.button = new FormattedButton(text, selected);
		if (dim != null) {
//			this.setPreferredSize(dim);
		}
		this.add(this.button);
	}

	public String getText() {
		return this.button.getText();
	}

	public void addActionListener(ActionListener listener) {
		this.button.addActionListener(listener);
	}

	public void setActionCommand(String command) {
		this.button.setActionCommand(command);
	}

	public String getActionCommand() {
		return this.button.getActionCommand();
	}

	public FormattedButton getButton() {
		return this.button;
	}

	public class FormattedButton extends JButton {

		private static final long serialVersionUID = 5467086225894783138L;

		/** The selected. */
		private boolean selected;

		private FormattedButton(String text, boolean selected) {
//			this.button.setOpaque(false);
//			this.button.setContentAreaFilled(false);
			this.setText(text);
			this.setMargin(new Insets(0, 0, 0, 0));
			this.setFocusPainted(false);
			this.setBorderPainted(false);
			this.setSelectedColor(selected);
		}

		public void setSelectedColor(boolean selected) {
			this.selected = selected;
			this.setBackground(selected ? FormattedJPanel.selectedColor : FormattedJPanel.notSelectedColor);
		}

		public boolean isSelectedColor() {
			return this.selected;
		}

	}

}