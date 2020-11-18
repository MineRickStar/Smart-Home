package gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import start.Period;
import start.Room;
import start.Task;
import start.TaskHandler;
import start.Time;
import utils.Background;

/**
 * The Class ViewTask.
 *
 * @author MineRickStar
 */
public class ViewTask extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1967061927909870401L;

	/** The layout. */
	private SpringLayout layout;

	/** The nameLabel. */
	private JLabel nameLabel;

	/** The nameLabel field. */
	private JTextField nameField;

	/** The description. */
	private JLabel description;

	/** The descriptionArea. */
	private JTextArea descriptionArea;

	/** The periodLabel. */
	private JLabel periodLabel;

	/** The periods. */
	private List<JButton> periods;

	/** The times. */
	private List<JButton> times;

	/** The rooms. */
	private List<JButton> rooms;

	/** The ok. */
	private JButton ok;

	/**
	 * Instantiates a new view task.
	 *
	 * @param parent the parent
	 */
	public ViewTask(BackgroundPanel parent) {
		super(parent, Background.load("Slim Black Screen.jpg"));
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		this.initButtons();
		this.addButtons();

		this.addActionListener(this.periods, true);
		this.addActionListener(this.times, false);
		this.addActionListenerToRooms(this.rooms);
	}

	/**
	 * Inits the Buttons.
	 */
	private void initButtons() {
		this.nameLabel = new JLabel("Name:");
		this.nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		this.nameLabel.setForeground(Color.WHITE);

		this.nameField = new JTextField(15);
		this.nameField.setBorder(null);

		this.description = new JLabel("Beschreibung:");
		this.description.setForeground(Color.WHITE);

		this.descriptionArea = new JTextArea(3, 15);

		this.periodLabel = new JLabel("Wiederholung:");
		this.periodLabel.setForeground(Color.WHITE);

		this.periods = Arrays.stream(Period.values())
				.map(p -> new JButton(p.getName()))
				.peek(b -> b.setBackground(b.getText()
						.equals("Jede Woche") ? Color.GREEN : Color.BLUE))
				.collect(Collectors.toList());

		this.times = Arrays.stream(Time.values())
				.map(t -> new JButton(t.getDisplayName()))
				.peek(b -> b.setBackground(b.getText()
						.equals("Am Tag") ? Color.GREEN : Color.BLUE))
				.collect(Collectors.toList());

		this.rooms = Arrays.stream(Room.values())
				.map(t -> new JButton(t.getName()))
				.peek(b -> b.setBackground(Color.BLUE))
				.collect(Collectors.toList());

		this.ok = new JButton("OK");

		this.ok.addActionListener(e -> {
			String name = this.nameField.getText();
			String description = this.descriptionArea.getText();

			Task task = TaskHandler.getInstance()
					.createTask(name, null, this.period.getJavaPeriod(), this.selectedRooms);
			task.addDescription(description);
		});
	}

	/**
	 * Adds the buttons.
	 */
	private void addButtons() {

		this.layout.putConstraint(SpringLayout.WEST, this.nameLabel, 630, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.nameLabel, 320, SpringLayout.NORTH, this);

		this.add(this.nameLabel);

		this.layout.putConstraint(SpringLayout.WEST, this.nameField, 80, SpringLayout.EAST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.NORTH, this.nameField, 0, SpringLayout.NORTH, this.nameLabel);

		this.add(this.nameField);

		this.layout.putConstraint(SpringLayout.WEST, this.description, 0, SpringLayout.WEST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.NORTH, this.description, 10, SpringLayout.SOUTH, this.nameLabel);

		this.add(this.description);

		JPanel startDate = this.createStartDatePanel();

		this.layout.putConstraint(SpringLayout.WEST, startDate, 0, SpringLayout.WEST, this.description);
		this.layout.putConstraint(SpringLayout.EAST, startDate, 0, SpringLayout.EAST, this.descriptionArea);
		this.layout.putConstraint(SpringLayout.NORTH, startDate, 10, SpringLayout.SOUTH, this.descriptionArea);

		this.add(startDate);

		this.layout.putConstraint(SpringLayout.WEST, this.descriptionArea, 0, SpringLayout.WEST, this.nameField);
		this.layout.putConstraint(SpringLayout.NORTH, this.descriptionArea, 0, SpringLayout.NORTH, this.description);

		this.add(this.descriptionArea);

		JPanel datesAndTimes = this.createDatesAndTimesPanel();

		this.layout.putConstraint(SpringLayout.WEST, datesAndTimes, 40, SpringLayout.EAST, this.nameField);
		this.layout.putConstraint(SpringLayout.NORTH, datesAndTimes, 0, SpringLayout.NORTH, this.nameField);

		this.add(datesAndTimes);

		this.layout.putConstraint(SpringLayout.WEST, this.periodLabel, 0, SpringLayout.WEST, datesAndTimes);
		this.layout.putConstraint(SpringLayout.SOUTH, this.periodLabel, Spring.constant(-20), SpringLayout.NORTH,
				datesAndTimes);

		this.add(this.periodLabel);

		this.layout.putConstraint(SpringLayout.SOUTH, this.ok, -50, SpringLayout.NORTH, this.nameLabel);
		this.layout.putConstraint(SpringLayout.WEST, this.ok, 0, SpringLayout.WEST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.EAST, this.ok, 0, SpringLayout.EAST, this.nameField);

		this.add(this.ok);

	}

	/**
	 * Creates the start date panel.
	 *
	 * @return the j panel
	 */
	private JPanel createStartDatePanel() {
		JPanel startDate = new JPanel();
		startDate.setLayout(new GridLayout(5, 7, 10, 10));

		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.WEEK_OF_YEAR);

		LocalDateTime addDate = LocalDateTime.now();
		for (int i = 0; i < 35; i++) {
			// TODO
			startDate.add(new JButton(addDate.format(DateTimeFormatter.ofPattern("dd.MM"))));
			addDate = addDate.plusDays(1);
		}

		startDate.setOpaque(false);

		return startDate;
	}

	/**
	 * Creates the Dates and Times Panel where all periods and start times are
	 * displayed.
	 *
	 * @return the Dates and Times Panel
	 */
	private JPanel createDatesAndTimesPanel() {
		JPanel datesAndTimes = new JPanel();
		GroupLayout layout = new GroupLayout(datesAndTimes);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		datesAndTimes.setLayout(layout);
		datesAndTimes.setOpaque(false);

		layout.linkSize(this.periods.toArray(JButton[]::new));
		layout.linkSize(this.times.toArray(JButton[]::new));
		layout.linkSize(this.rooms.toArray(JButton[]::new));

		ParallelGroup trailingGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		this.periods.forEach(trailingGroup::addComponent);

		ParallelGroup centerGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
		this.times.forEach(centerGroup::addComponent);

		ParallelGroup leadingGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		this.rooms.forEach(leadingGroup::addComponent);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(trailingGroup)
				.addGroup(centerGroup)
				.addGroup(leadingGroup));

		SequentialGroup sGroup = layout.createSequentialGroup();

		for (int i = 0, n = Math.max(Math.max(this.periods.size(), this.times.size()), this.rooms.size()); i < n; i++) {
			ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
			if (this.periods.size() > i) {
				group.addComponent(this.periods.get(i));
			}
			if (this.times.size() > i) {
				group.addComponent(this.times.get(i));
			}
			if (this.rooms.size() > i) {
				group.addComponent(this.rooms.get(i));
			}
			sGroup.addGroup(group);
		}

		layout.setVerticalGroup(sGroup);
		return datesAndTimes;
	}

	/**
	 * Adds the action listener.
	 *
	 * @param buttons the buttons
	 * @param period  the period
	 */
	private void addActionListener(List<JButton> buttons, boolean period) {
		buttons.forEach(button -> {
			button.addActionListener(e -> {
				JButton source = (JButton) e.getSource();
				for (int i = 0; i < buttons.size(); i++) {
					JButton button0 = buttons.get(i);
					if (button0.equals(source)) {
						if (period) {
							this.period = Period.values()[i];
						} else {
							this.time = Time.values()[i];
						}
						button0.setBackground(Color.GREEN);
					} else {
						button0.setBackground(Color.BLUE);
					}
				}
			});
		});
	}

	/**
	 * Adds the action listener to rooms.
	 *
	 * @param buttons the buttons
	 */
	private void addActionListenerToRooms(List<JButton> buttons) {
		buttons.forEach(button -> {
			button.addActionListener(e -> {
				JButton source = (JButton) e.getSource();
				for (int i = 0; i < buttons.size(); i++) {
					JButton button0 = buttons.get(i);
					if (button0.equals(source)) {
						if (source.getBackground()
								.equals(Color.GREEN)) {
							this.selectedRooms.remove(Room.values()[i]);
							button0.setBackground(Color.BLUE);
						} else {
							this.selectedRooms.add(Room.values()[i]);
							button0.setBackground(Color.GREEN);
						}
					}
				}
			});
		});
	}

	/** The periodLabel. */
	private Period period = Period.EACH_WEEK;

	/** The time. */
	private Time time = Time.AT_THE_DAY;

	/** The selected rooms. */
	private ArrayList<Room> selectedRooms = new ArrayList<>();

}