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

	/** The add task button. */
	private JButton addTaskButton;

	/** The show tasks. */
	private JButton showTasks;

	/**
	 * Instantiates a new view default.
	 *
	 * @param parent the parent
	 */
	public ViewDefault(BackgroundPanel parent) {
		super(parent, "Black screen.png");
		this.initButtons();
		this.addButtons();
	}

	/**
	 * Inits the buttons.
	 */
	private void initButtons() {
		this.addTaskButton = new JButton("Aufgabe hinzufügen");
		this.addTaskButton.addActionListener(e -> this.parent.changeView(View.ADD_TASK));

		this.showTasks = new JButton("Aufgaben anschauen");
		this.showTasks.addActionListener(e -> this.parent.changeView(View.VIEW_TASKS));
	}

	/**
	 * Adds the buttons.
	 */
	private void addButtons() {
		this.layout.putConstraint(SpringLayout.WEST, this.addTaskButton, 100, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.addTaskButton, 100, SpringLayout.NORTH, this);
		this.add(this.addTaskButton);
		this.layout.putConstraint(SpringLayout.WEST, this.showTasks, 100, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.showTasks, 200, SpringLayout.NORTH, this);
		this.add(this.showTasks);
	}

}