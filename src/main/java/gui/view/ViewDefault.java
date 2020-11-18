package gui.view;

import javax.swing.JButton;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;
import utils.Background;

/**
 * The Class ViewDefault.
 *
 * @author MineRickStar
 */
public class ViewDefault extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7628654127485185609L;

	/** The add task button. */
	private JButton addTaskButton;

	/**
	 * Instantiates a new view default.
	 *
	 * @param parent the parent
	 */
	public ViewDefault(BackgroundPanel parent) {
		super(parent, Background.load("Black screen.png"));
		this.initButtons();
		this.addButtons();
	}

	/**
	 * Inits the buttons.
	 */
	private void initButtons() {
		this.addTaskButton = new JButton("Aufgabe hinzufügen");
		this.addTaskButton.addActionListener(e -> this.parent.changeView(View.ADD_TASK));
	}

	/**
	 * Adds the buttons.
	 */
	private void addButtons() {
		this.add(this.addTaskButton);
	}

}