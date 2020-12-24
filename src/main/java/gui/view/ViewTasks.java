package gui.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;
import start.Task;
import start.TaskHandler;
import user.Inhabitant;

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
	private JPanel taskPanel;

	/**
	 * View task.
	 *
	 * @param parent the parent
	 */
	public ViewTasks(BackgroundPanel parent) {
		super(parent);
		this.inhabitantsToChooseAndAllTasks = new ArrayList<>();
		this.initButtons();
		this.choosePanel = this.createChoosePanel();
		this.addLayout();
		this.add(this.choosePanel);
		this.addBackButton();
	}

	/**
	 * Inits the buttons.
	 */
	private void initButtons() {
		JButton allTasks = new JButton("Alle Aufgaben");
		allTasks.addActionListener(e -> this.changeViewToInhabitant(null));
		this.inhabitantsToChooseAndAllTasks.add(allTasks);
		Arrays.stream(Inhabitant.values())
				.filter(i -> i.defaultUser)
				.forEach(i -> {
					JButton b = new JButton(i.getName());
					b.addActionListener(e -> this.changeViewToInhabitant(i));
					this.inhabitantsToChooseAndAllTasks.add(b);
				});
	}

	/**
	 * Adds the layout.
	 */
	private void addLayout() {
		this.layout.putConstraint(SpringLayout.WEST, this.choosePanel, 200, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.choosePanel, 200, SpringLayout.NORTH, this);
	}

	/**
	 * Creates the inhabitants panel.
	 *
	 * @return the j panel
	 */
	private JPanel createChoosePanel() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		panel.setLayout(layout);
		panel.setOpaque(false);

		layout.linkSize(this.inhabitantsToChooseAndAllTasks.toArray(JButton[]::new));

		ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		this.inhabitantsToChooseAndAllTasks.forEach(b -> {
			horizontalGroup.addComponent(b);
			verticalGroup.addComponent(b)
					.addGap(40);
		});

		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);

		return panel;
	}

	/**
	 * Change view to inhabitant.
	 *
	 * @param inhabitant the inhabitant
	 */
	private void changeViewToInhabitant(Inhabitant inhabitant) {
		this.changeBackListener(e -> {
			this.remove(this.taskPanel);
			this.choosePanel.setVisible(true);
			this.changeBackview(View.DEFAULT);
			this.revalidate();
			this.repaint();
		});
		this.choosePanel.setVisible(false);
		List<Task> tasks;
		if (inhabitant == null) {
			tasks = TaskHandler.getInstance()
					.getAllTasks();
		} else {
			tasks = TaskHandler.getInstance()
					.getTasksFor(inhabitant);
		}
		this.taskPanel = this.createTaskPanel(tasks);

		this.layout.putConstraint(SpringLayout.WEST, this.taskPanel, 100, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.taskPanel, 150, SpringLayout.NORTH, this);
		this.add(this.taskPanel);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Creates the task panel.
	 *
	 * @param tasks the tasks
	 * @return the j panel
	 */
	private JPanel createTaskPanel(List<Task> tasks) {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		panel.setLayout(layout);
		panel.setOpaque(false);

		ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();

		JPanel labelPanel = Task.getLabelPanel();
		horizontalGroup.addComponent(labelPanel);
		verticalGroup.addComponent(labelPanel);
		verticalGroup.addGap(40, 40, Short.MAX_VALUE);

		tasks.stream()
				.sorted()
				.map(Task::getPanel)
				.forEach(panel0 -> {
					horizontalGroup.addComponent(panel0);
					verticalGroup.addComponent(panel0)
							.addGap(20);
				});

		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);

		return panel;
	}

}