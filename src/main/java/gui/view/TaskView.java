package gui.view;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;

public class TaskView extends AbstractView {

	private static final long serialVersionUID = 1259217160346559733L;

	/** The add task button. */
	private JButton addTaskButton;

	/** The show tasks. */
	private JButton showTasks;

	public TaskView(BackgroundPanel parent) {
		super(parent);
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
		this.addBackButton();
	}

}