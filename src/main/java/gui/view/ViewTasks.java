package gui.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import start.Task;
import start.TaskHandler;
import user.Inhabitant;
import user.Person;

/**
 * The Class ViewTasks.
 *
 * @author MineRickStar
 */
public class ViewTasks extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4721132931833018089L;

	/** The inhabitants to choose. */
	private final List<JButton> inhabitantsToChooseAndAllTasks;

	/** The choose panel. */
	private final JPanel choosePanel;

	/** The task panel. */
	private final JPanel taskPanel;

	/**
	 * View task.
	 *
	 * @param parent the parent
	 */
	public ViewTasks(BackgroundPanel parent) {
		super(parent, "Star Trek.png");
		this.inhabitantsToChooseAndAllTasks = new ArrayList<>();
		this.initButtons();
		this.choosePanel = new JPanel();
		this.createChoosePanel();
		this.taskPanel = new JPanel();
		this.addBackButton();
		this.addButtons();
	}

	/**
	 * Inits the buttons.
	 */
	private void initButtons() {
		JButton allTasks = new JButton("Alle Aufgaben");
		allTasks.addActionListener(e -> this.changeViewToInhabitant(null));
		this.inhabitantsToChooseAndAllTasks.add(allTasks);
		Person.getInhabitants()
				.forEach(i -> {
					JButton b = new JButton(i.getName());
					b.addActionListener(e -> this.changeViewToInhabitant(i));
					this.inhabitantsToChooseAndAllTasks.add(b);
				});
	}

	/**
	 * Adds the buttons.
	 */
	private void addButtons() {
		this.layout.putConstraint(SpringLayout.WEST, this.choosePanel, 200, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.choosePanel, 200, SpringLayout.NORTH, this);

		this.add(this.choosePanel);
	}

	/**
	 * Creates the inhabitants panel.
	 *
	 * @return the j panel
	 */
	private void createChoosePanel() {
		GroupLayout layout = new GroupLayout(this.choosePanel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		this.choosePanel.setLayout(layout);
		this.choosePanel.setOpaque(false);

		layout.linkSize(this.inhabitantsToChooseAndAllTasks.toArray(JButton[]::new));

		ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		this.inhabitantsToChooseAndAllTasks.forEach(b -> {
			horizontalGroup.addComponent(b);
			verticalGroup.addComponent(b);
			verticalGroup.addGap(40, 40, Short.MAX_VALUE);
		});

		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
	}

	/**
	 * Change view to inhabitant.
	 *
	 * @param inhabitant the inhabitant
	 */
	private void changeViewToInhabitant(Inhabitant inhabitant) {
		this.remove(this.backButton);
		this.remove(this.choosePanel);
		List<Task> tasks;
		if (inhabitant == null) {
			tasks = TaskHandler.getInstance()
					.getAllTasks();
		} else {
			tasks = TaskHandler.getInstance()
					.getTasksFor(inhabitant);
		}
		this.createTaskPanel(tasks);
		this.layout.putConstraint(SpringLayout.WEST, this.taskPanel, 200, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.taskPanel, 200, SpringLayout.NORTH, this);
		this.add(this.taskPanel);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Creates the task panel.
	 *
	 * @param tasks the tasks
	 */
	private void createTaskPanel(List<Task> tasks) {
		GroupLayout layout = new GroupLayout(this.taskPanel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		this.taskPanel.setLayout(layout);
		this.taskPanel.setOpaque(false);

		ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();

		tasks.stream()
				.map(task -> task.getPanel())
				.forEach(panel -> {
					horizontalGroup.addComponent(panel);
					verticalGroup.addComponent(panel);
					verticalGroup.addGap(40, 40, Short.MAX_VALUE);
				});

		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
	}

}