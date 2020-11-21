package gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import gui.BackgroundPanel.View;
import start.Period;
import start.Room;
import start.Task;
import start.TaskHandler;
import start.Time;
import user.Inhabitant;
import utils.FormattedJButton;

/**
 * The Class ViewTaskAdd.
 *
 * @author MineRickStar
 */
public class ViewTaskAdd extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1967061927909870401L;

	/** The nameLabel. */
	private JLabel nameLabel;

	/** The nameLabel field. */
	private JTextField nameField;

	/** The descriptionLabel. */
	private JLabel descriptionLabel;

	/** The descriptionArea. */
	private JTextArea descriptionArea;

	/** The days. */
	private List<FormattedJButton> days;

	/** The periods. */
	private List<FormattedJButton> periods;

	/** The times. */
	private List<FormattedJButton> times;

	/** The rooms. */
	private Map<FormattedJButton, Room> rooms;

	/** The finishers. */
	private Map<FormattedJButton, Inhabitant> finishers;

	/** The ok. */
	private JButton ok;

	/**
	 * Instantiates a new view task.
	 *
	 * @param parent the parent
	 */
	public ViewTaskAdd(BackgroundPanel parent) {
		super(parent);
		this.initButtons();
		this.addButtons();

		this.addActionListener(this.periods, true);
		this.addActionListener(this.times, false);
		this.addActionListenerToRooms();
		this.addActionListenerToDays();
		this.addActionListenerToFinisher();
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

		this.descriptionLabel = new JLabel("Beschreibung:");
		this.descriptionLabel.setForeground(Color.WHITE);

		this.descriptionArea = new JTextArea(3, 15);

		this.days = IntStream.range(0, 35)
				.boxed()
				.map(i -> new FormattedJButton(LocalDateTime.now()
						.plusDays(i)
						.format(this.buttonFormatter), false))
				.collect(Collectors.toList());

		this.periods = Arrays.stream(Period.values())
				.map(p -> new FormattedJButton(p.getName(), false))
				.peek(b -> b.setSelectedColor(b.getText()
						.equals("Jede Woche")))
				.collect(Collectors.toList());

		this.times = Arrays.stream(Time.values())
				.map(t -> new FormattedJButton(t.getDisplayName(), false))
				.peek(b -> b.setSelectedColor(b.getText()
						.equals("Am Tag")))
				.collect(Collectors.toList());

		this.rooms = Arrays.stream(Room.values())
				.collect(Collectors.toMap(r -> new FormattedJButton(r.getName(), false), r -> r));

		this.finishers = Arrays.stream(Inhabitant.values())
				.collect(Collectors.toMap(i -> new FormattedJButton(i.getName(), true), t -> t));

		this.ok = new JButton("OK");

		this.ok.addActionListener(e -> {
			String name = this.nameField.getText();
			String description = this.descriptionArea.getText();
			if (name.isBlank() && description.isBlank() && this.selectedRooms.isEmpty()) { return; }

			Task task = TaskHandler.getInstance()
					.createTask(name, this.start, this.period, this.time);
			task.addDescription(description);
			if (!this.selectedRooms.isEmpty()) {
				task.addRooms(this.selectedRooms);
			}
			task.addTaskForThem(this.presumableFinishers);
			task.addRooms(this.selectedRooms);
			this.parent.changeView(View.DEFAULT);
			TaskHandler.getInstance()
					.writeCSV();
		});
	}

	/**
	 * Adds the buttons.
	 */
	private void addButtons() {
		// BackButton
		this.addBackButton();
		// NameLabel
		this.layout.putConstraint(SpringLayout.WEST, this.nameLabel, Spring.constant(10, 10, 50), SpringLayout.WEST,
				this);
		this.layout.putConstraint(SpringLayout.NORTH, this.nameLabel, Spring.constant(10, 50, 50), SpringLayout.SOUTH,
				this.backButton);
		this.add(this.nameLabel);
		// DescriptionLabel
		this.layout.putConstraint(SpringLayout.WEST, this.descriptionLabel, 0, SpringLayout.WEST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.NORTH, this.descriptionLabel, 10, SpringLayout.SOUTH, this.nameLabel);
		this.add(this.descriptionLabel);
		// StartDateButtons
		JPanel startDate = this.createStartDatePanel();
		this.layout.putConstraint(SpringLayout.WEST, startDate, 0, SpringLayout.WEST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.NORTH, startDate, 10, SpringLayout.SOUTH, this.descriptionArea);
		this.add(startDate);
		// NameField
		this.layout.putConstraint(SpringLayout.WEST, this.nameField, Spring.constant(0, 0, Short.MAX_VALUE),
				SpringLayout.EAST, this.nameLabel);
		this.layout.putConstraint(SpringLayout.WEST, this.nameField, 0, SpringLayout.WEST, this.descriptionArea);
		this.layout.putConstraint(SpringLayout.EAST, this.nameField, 0, SpringLayout.EAST, startDate);
		this.layout.putConstraint(SpringLayout.NORTH, this.nameField, 0, SpringLayout.NORTH, this.nameLabel);
		this.add(this.nameField);
		// DescriptionArea
		this.layout.putConstraint(SpringLayout.WEST, this.descriptionArea, Spring.constant(10, 10, Short.MAX_VALUE),
				SpringLayout.EAST, this.descriptionLabel);
		this.layout.putConstraint(SpringLayout.EAST, this.descriptionArea, 0, SpringLayout.EAST, startDate);
		this.layout.putConstraint(SpringLayout.NORTH, this.descriptionArea, 0, SpringLayout.NORTH,
				this.descriptionLabel);
		this.add(this.descriptionArea);
		// DatesAndTimesInfo // TODO pull it apart
		JPanel datesAndTimes = this.createDatesAndTimesPanel();
		this.layout.putConstraint(SpringLayout.WEST, datesAndTimes, 40, SpringLayout.EAST, startDate);
		this.layout.putConstraint(SpringLayout.NORTH, datesAndTimes, 0, SpringLayout.NORTH, this.nameField);
		this.add(datesAndTimes);
		// Finisher
		JPanel finisherPanel = this.createFinisherPanel();
		this.layout.putConstraint(SpringLayout.WEST, finisherPanel, 10, SpringLayout.EAST, datesAndTimes);
		this.layout.putConstraint(SpringLayout.NORTH, finisherPanel, 0, SpringLayout.NORTH, datesAndTimes);
		this.add(finisherPanel);
		// OK Button
		this.layout.putConstraint(SpringLayout.NORTH, this.ok, 0, SpringLayout.NORTH, this.backButton);
		this.layout.putConstraint(SpringLayout.WEST, this.ok, Spring.constant(50, 50, 100), SpringLayout.EAST,
				this.backButton);
		this.layout.putConstraint(SpringLayout.EAST, this.ok, 0, SpringLayout.EAST, this.nameField);
		this.add(this.ok);

	}

	/** The button formatter. */
	private final DateTimeFormatter buttonFormatter = DateTimeFormatter.ofPattern("dd.MM");

	/**
	 * Creates the start date panel.
	 *
	 * @return the j panel
	 */
	private JPanel createStartDatePanel() {
		JPanel startDate = new JPanel();
		startDate.setLayout(new GridLayout(6, 7, 10, 10));

		DayOfWeek day = LocalDate.now()
				.getDayOfWeek();

		Arrays.stream(DayOfWeek.values())
				.forEach(s -> {
					JLabel label = new JLabel(s.plus(day.getValue() - 1)
							.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
					label.setForeground(Color.WHITE);
					startDate.add(label);
				});

		this.days.forEach(startDate::add);

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

		layout.linkSize(this.periods.toArray(FormattedJButton[]::new));
		layout.linkSize(this.times.toArray(FormattedJButton[]::new));
		layout.linkSize(this.rooms.keySet()
				.toArray(FormattedJButton[]::new));

		ParallelGroup trailingGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		this.periods.forEach(trailingGroup::addComponent);

		ParallelGroup centerGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
		this.times.forEach(centerGroup::addComponent);

		ParallelGroup leadingGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		this.rooms.keySet()
				.forEach(leadingGroup::addComponent);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(trailingGroup)
				.addGroup(centerGroup)
				.addGroup(leadingGroup));

		SequentialGroup sGroup = layout.createSequentialGroup();

		List<FormattedJButton> roomKeys = new ArrayList<>(this.rooms.keySet());
		for (int i = 0, n = Math.max(Math.max(this.periods.size(), this.times.size()), this.rooms.size()); i < n; i++) {
			ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
			if (this.periods.size() > i) {
				group.addComponent(this.periods.get(i));
			}
			if (this.times.size() > i) {
				group.addComponent(this.times.get(i));
			}
			if (this.rooms.size() > i) {
				group.addComponent(roomKeys.get(i));
			}
			sGroup.addGroup(group);
		}

		layout.setVerticalGroup(sGroup);
		return datesAndTimes;
	}

	/**
	 * Creates the finisher panel.
	 *
	 * @return the j panel
	 */
	private JPanel createFinisherPanel() {
		JPanel finisherPanel = new JPanel();
		GroupLayout layout = new GroupLayout(finisherPanel);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		finisherPanel.setLayout(layout);
		finisherPanel.setOpaque(false);

		layout.linkSize(this.finishers.keySet()
				.toArray(FormattedJButton[]::new));

		ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		this.finishers.forEach((b, i) -> {
			horizontalGroup.addComponent(b);
			verticalGroup.addComponent(b);
		});

		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);

		return finisherPanel;
	}

	/**
	 * Adds the action listener.
	 *
	 * @param buttons the buttons
	 * @param period  the period
	 */
	private void addActionListener(List<FormattedJButton> buttons, boolean period) {
		buttons.forEach(button -> {
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				for (int i = 0; i < buttons.size(); i++) {
					FormattedJButton button0 = buttons.get(i);
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
	 */
	private void addActionListenerToRooms() {
		this.rooms.forEach((button, room) -> {
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				if (source.equals(button)) {
					if (button.isSelectedColor()) {
						this.selectedRooms.remove(room);
						button.setSelectedColor(false);
					} else {
						this.selectedRooms.add(room);
						button.setSelectedColor(true);
					}
				}
			});
		});
	}

	/**
	 * Adds the action listener to days.
	 */
	private void addActionListenerToDays() {
		for (int i = 0; i < this.days.size(); i++) {
			FormattedJButton button = this.days.get(i);
			button.setActionCommand(String.valueOf(i));
			if (i == 0) {
				button.setBackground(Color.GREEN);
			} else {
				button.setBackground(Color.BLUE);
			}
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				this.days.forEach(dayButton -> {
					if (dayButton.equals(source)) {
						dayButton.setBackground(Color.GREEN);
						this.start = LocalDateTime.now()
								.plusDays(Integer.parseInt(source.getActionCommand()));
					} else {
						dayButton.setBackground(Color.BLUE);
					}
				});
			});
		}
	}

	/**
	 * Adds the action listener to finisher.
	 */
	private void addActionListenerToFinisher() {
		this.finishers.forEach((button, inhabitant) -> {
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				if (source.equals(button)) {
					if (button.isSelectedColor()) {
						this.presumableFinishers.remove(inhabitant);
						button.setSelectedColor(false);
					} else {
						this.presumableFinishers.add(inhabitant);
						button.setSelectedColor(true);
					}
				}
			});
		});
	}

	/** The periodLabel. */
	private Period period = Period.EACH_WEEK;

	/** The time. */
	private Time time = Time.AT_THE_DAY;

	/** The start. */
	private LocalDateTime start = LocalDateTime.now();

	/** The selected rooms. */
	private List<Room> selectedRooms = new ArrayList<>();

	/** The presumable finishers. */
	private List<Inhabitant> presumableFinishers = Arrays.asList(Inhabitant.values());

}