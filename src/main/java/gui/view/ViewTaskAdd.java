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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;
import tasks.Floor;
import tasks.Period;
import tasks.Room;
import tasks.Task;
import tasks.TaskHandler;
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

	/** The rooms. */
	private List<FormattedJButton> rooms;

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

		this.addActionListenerToPeriods();
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

		this.rooms = Arrays.stream(Room.values())
				.sorted((o1, o2) -> {
					Floor f1 = o1.floor;
					Floor f2 = o2.floor;
					int comp = Integer.compare(f1.ordinal(), f2.ordinal());
					if (comp == 0) { return o1.name.compareTo(o2.name); }
					return comp;
				})
				.map(r -> {
					FormattedJButton b = new FormattedJButton(r.name, false);
					b.setAssociatedObject(r);
					return b;
				})
				.collect(Collectors.toList());

		this.finishers = Arrays.stream(Inhabitant.values())
				.filter(i -> i.defaultUser)
				.collect(Collectors.toMap(i -> new FormattedJButton(i.getName(), true), Function.identity()));

		this.ok = new JButton("OK");

		this.ok.addActionListener(e -> {
			String name = this.nameField.getText();
			String description = this.descriptionArea.getText();
			if (name.isBlank() && description.isBlank() && this.selectedRooms.isEmpty()) { return; }

			Task task = TaskHandler.getInstance()
					.createTask(name, this.start, this.period);
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
		this.addBackButton(View.TASKS);
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
		// Periods
		JPanel periods = this.createPeriodsPanel();
		this.layout.putConstraint(SpringLayout.WEST, periods, 40, SpringLayout.EAST, startDate);
		this.layout.putConstraint(SpringLayout.NORTH, periods, 0, SpringLayout.NORTH, this.nameField);
		this.add(periods);
		// Rooms
		JPanel rooms = this.createRoomsPanel();
		this.layout.putConstraint(SpringLayout.WEST, rooms, 40, SpringLayout.EAST, periods);
		this.layout.putConstraint(SpringLayout.NORTH, rooms, 0, SpringLayout.NORTH, this.nameField);
		this.add(rooms);
		// Finisher
		JPanel finisherPanel = this.createFinisherPanel();
		this.layout.putConstraint(SpringLayout.WEST, finisherPanel, 10, SpringLayout.EAST, rooms);
		this.layout.putConstraint(SpringLayout.NORTH, finisherPanel, 0, SpringLayout.NORTH, rooms);
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
	private JPanel createPeriodsPanel() {
		JPanel periods = new JPanel();
		GroupLayout layout = new GroupLayout(periods);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		periods.setLayout(layout);
		periods.setOpaque(false);

		layout.linkSize(this.periods.toArray(FormattedJButton[]::new));

		ParallelGroup trailingGroup = layout.createParallelGroup();
		this.periods.forEach(trailingGroup::addComponent);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(trailingGroup));

		SequentialGroup sGroup = layout.createSequentialGroup();

		for (int i = 0, n = this.periods.size(); i < n; i++) {
			if (this.periods.size() > i) {
				sGroup.addComponent(this.periods.get(i));
			}
		}

		layout.setVerticalGroup(sGroup);
		return periods;
	}

	/**
	 * The create rooms panel.
	 *
	 * @return the j panel
	 */
	private JPanel createRoomsPanel() {
		JPanel rooms = new JPanel();

		DefaultListModel<String> dlm = new DefaultListModel<>();
		JList<String> list = new JList<>(dlm);
		list.setFixedCellHeight(24);
		list.setVisibleRowCount(12);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.rooms.stream()
				.map(r -> ((Room) r.getAssociatedObject()).name)
				.forEach(dlm::addElement);

		JScrollPane pane = new JScrollPane(list);

		rooms.add(pane);

		return rooms;
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
	 */
	private void addActionListenerToPeriods() {
		this.periods.forEach(button -> {
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				for (int i = 0; i < this.periods.size(); i++) {
					FormattedJButton button0 = this.periods.get(i);
					this.period = Period.values()[i];
					if (button0.equals(source)) {
						button0.setSelectedColor(true);
					} else {
						button0.setSelectedColor(false);
					}
				}
			});
		});
	}

	/**
	 * Adds the action listener to rooms.
	 */
	private void addActionListenerToRooms() {
		this.rooms.forEach(button -> {
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				if (source.equals(button)) {
					if (button.isSelectedColor()) {
						this.selectedRooms.remove(button.getAssociatedObject());
						button.setSelectedColor(false);
					} else {
						this.selectedRooms.add((Room) button.getAssociatedObject());
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
				button.setSelectedColor(true);
			} else {
				button.setSelectedColor(false);
			}
			button.addActionListener(e -> {
				FormattedJButton source = (FormattedJButton) e.getSource();
				this.days.forEach(dayButton -> {
					if (dayButton.equals(source)) {
						dayButton.setSelectedColor(true);
						this.start = LocalDateTime.now()
								.plusDays(Integer.parseInt(source.getActionCommand()));
					} else {
						dayButton.setSelectedColor(false);
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

	/** The start. */
	private LocalDateTime start = LocalDateTime.now();

	/** The selected rooms. */
	private List<Room> selectedRooms = new ArrayList<>();

	/** The presumable finishers. */
	private List<Inhabitant> presumableFinishers = new ArrayList<>(Arrays.asList(Inhabitant.values()));

}