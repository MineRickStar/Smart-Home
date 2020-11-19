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
	private List<JButton> days;

	/** The periods. */
	private List<JButton> periods;

	/** The times. */
	private List<JButton> times;

	/** The rooms. */
	private List<JButton> rooms;

	/** The back. */
	private JButton back;

	/** The ok. */
	private JButton ok;

	/**
	 * Instantiates a new view task.
	 *
	 * @param parent the parent
	 */
	public ViewTaskAdd(BackgroundPanel parent) {
		super(parent, "Slim Black Screen.jpg");
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

		this.descriptionLabel = new JLabel("Beschreibung:");
		this.descriptionLabel.setForeground(Color.WHITE);

		this.descriptionArea = new JTextArea(3, 15);

		this.days = IntStream.range(0, 35)
				.boxed()
				.map(i -> {
					JButton button = new JButton(LocalDateTime.now()
							.plusDays(i)
							.format(this.buttonFormatter));
					button.setActionCommand(i.toString());
					if (i == 0) {
						button.setBackground(Color.GREEN);
					} else {
						button.setBackground(Color.BLUE);
					}
					button.addActionListener(e -> {
						JButton source = (JButton) e.getSource();
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
					return button;
				})
				.collect(Collectors.toList());

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

		this.back = new JButton("Zurück");

		this.back.addActionListener(e -> this.parent.changeView(View.DEFAULT));

		this.ok = new JButton("OK");

		this.ok.addActionListener(e -> {
			String name = this.nameField.getText();
			String description = this.descriptionArea.getText();
			if (name.isBlank() && description.isBlank() && this.selectedRooms.isEmpty()) {
				return;
			}

			Task task = TaskHandler.getInstance()
					.createTask(name, this.start, this.period.getJavaPeriod(), this.time, this.selectedRooms);
			task.addDescription(description);
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
		this.layout.putConstraint(SpringLayout.WEST, this.nameLabel, 500, SpringLayout.WEST, this);
		this.layout.putConstraint(SpringLayout.NORTH, this.nameLabel, 320, SpringLayout.NORTH, this);
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
		// DatesAndTimesInfo
		JPanel datesAndTimes = this.createDatesAndTimesPanel();
		this.layout.putConstraint(SpringLayout.WEST, datesAndTimes, 40, SpringLayout.EAST, startDate);
		this.layout.putConstraint(SpringLayout.NORTH, datesAndTimes, 0, SpringLayout.NORTH, this.nameField);
		this.add(datesAndTimes);
		// OK Button
		this.layout.putConstraint(SpringLayout.SOUTH, this.ok, -50, SpringLayout.NORTH, this.nameLabel);
		this.layout.putConstraint(SpringLayout.WEST, this.ok, 0, SpringLayout.WEST, this.nameLabel);
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

	/** The start. */
	private LocalDateTime start = LocalDateTime.now();

	/** The selected rooms. */
	private ArrayList<Room> selectedRooms = new ArrayList<>();

}