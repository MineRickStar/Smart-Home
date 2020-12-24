package gui.view;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;

/**
 * The Class ViewDefault.
 *
 * @author MineRickStar
 */
public class ViewDefault extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7628654127485185609L;

	private JButton tasksButton;

	/**
	 * Instantiates a new view default.
	 *
	 * @param parent the parent
	 */
	public ViewDefault(BackgroundPanel parent) {
		super(parent);
		this.initButtons();
		this.addButtons();
	}

	private void initButtons() {
		this.tasksButton = new JButton("Aufgaben");
		this.tasksButton.addActionListener(e -> this.parent.changeView(View.TASKS));
	}

	private void addButtons() {
		this.layout.putConstraint(SpringLayout.WEST, this.tasksButton, 100, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.tasksButton, 100, SpringLayout.NORTH, this);
		this.add(this.tasksButton);
	}

}