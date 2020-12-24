package tasks;

import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import user.Inhabitant;

/**
 * The Class Task.
 */
public class Task implements Comparable<Task> {

	/** The task ID. */
	private final TaskID taskID;

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The create date. */
	private final LocalDateTime createDate;

	/** The period. */
	private Period period;

	/** The next due date. */
	private LocalDateTime nextDueDate;

	/** The task for them. */
	private List<Inhabitant> suitableFor;

	/** The finished. */
	private final HashMap<Inhabitant, List<LocalDateTime>> finished;

	/** The rooms. */
	private List<Room> rooms;

	/**
	 * Instantiates a new task.
	 *
	 * @param line the line
	 */
	Task(String line) {
		String[] fields = line.split(",");
		this.taskID = TaskID.of(fields[0]);
		this.name = fields[1];
		this.description = fields[2];
		this.createDate = LocalDateTime.parse(fields[3]);
		this.period = Period.parse(fields[4]);
		this.nextDueDate = LocalDateTime.parse(fields[5]);
		if (!fields[6].isEmpty()) {
			this.suitableFor = Arrays.stream(fields[6].split("/"))
					.map(Inhabitant::getInhabitantFromName)
					.collect(Collectors.toList());
		}
		this.finished = new HashMap<>();
		if (!fields[7].isEmpty()) {
			this.rooms = Arrays.stream(fields[7].split("/"))
					.map(Room::valueOf)
					.collect(Collectors.toList());
		}
	}

	/**
	 * Instantiates a new task.
	 *
	 * @param name         the name
	 * @param createDate   the create date
	 * @param nextDueDate  the next due date
	 * @param period       the period
	 * @param whenToFinish the when to finish
	 */
	public Task(String name, LocalDateTime createDate, LocalDateTime nextDueDate, Period period) {
		this.taskID = TaskID.getRandomID();
		this.name = name;
		this.createDate = createDate;
		this.nextDueDate = nextDueDate;
		this.period = period;
		this.finished = new HashMap<>();
		this.suitableFor = new ArrayList<>();
	}

	/**
	 * Adds the description.
	 *
	 * @param description the description
	 */
	public void addDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * Adds the rooms.
	 *
	 * @param rooms the rooms
	 */
	public void addRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	/**
	 * Marks the Task as completed for now and starts the Time again.
	 *
	 * @param who  the who
	 * @param when the when
	 */
	public void finished(Inhabitant who, LocalDateTime when) {
		this.finished.compute(who, (k, v) -> {
			if (v == null) {
				return Arrays.asList(when);
			} else {
				v.add(when);
				return v;
			}
		});
		this.nextDueDate = when.plus(this.period.getJavaPeriod());
	}

	public LocalDateTime getNextDueDate() {
		return this.nextDueDate;
	}

	/**
	 * Adds the presumable finisher.
	 *
	 * @param finisher the finisher
	 */
	public void addTaskForThem(List<Inhabitant> finisher) {
		finisher.forEach(this::addTaskForThem);
	}

	public List<Inhabitant> getTaskForThem() {
		return this.suitableFor;
	}

	/**
	 * Adds the presumable finisher.
	 *
	 * @param inhabitant the inhabitant
	 */
	public void addTaskForThem(Inhabitant inhabitant) {
		if (!this.suitableFor.contains(inhabitant)) {
			this.suitableFor.add(inhabitant);
		}
	}

	public static String getCSVFields() {
		return String.join(",", Arrays.asList("taskID", "name", "description", "createDate", "period", "nextDueDate",
				"suitableFor", "whenToFinish", "rooms"));
	}

	/**
	 * To CSV string.
	 *
	 * @return the string
	 */
	public String toCSVString() {
		StringBuilder sb = new StringBuilder(128);
		sb.append(this.taskID.ID.toString() + ",");
		sb.append(this.name + ",");
		if (this.description != null) {
			sb.append(this.description);
		}
		sb.append(",");
		sb.append(this.createDate.toString() + ",");
		sb.append(this.period.toString() + ",");
		sb.append(this.nextDueDate.toString() + ",");
		if (this.suitableFor != null) {
			sb.append(this.suitableFor.stream()
					.map(Inhabitant::getName)
					.collect(Collectors.joining("/"))
					.toString());
		}
		sb.append(",");
		if (this.rooms != null) {
			sb.append(this.rooms.stream()
					.map(Room::name)
					.collect(Collectors.joining("/"))
					.toString());
		}
		sb.append(",");
		return sb.toString();
	}

	/** The Constant formatter. */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

	public static JPanel getLabelPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 7, 10, 10));
		// Top row
		panel.add(new JLabel("Name"));
		panel.add(new JLabel("Beschreibung"));
		panel.add(new JLabel("Erstelldatum"));
		panel.add(new JLabel("Periode"));
		panel.add(new JLabel("Nächstes mal fällig"));
		panel.add(new JLabel("Für wen"));
		panel.add(new JLabel("Räume"));
		return panel;
	}

	public JPanel getPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 7, 10, 10));
		// Value row
		panel.add(new JLabel(this.name));
		panel.add(new JLabel(this.description));
		panel.add(new JLabel(this.createDate.format(Task.formatter)));
		panel.add(new JLabel(this.period.toString()));
		panel.add(new JLabel(this.nextDueDate.format(Task.formatter)));
		if ((this.suitableFor == null) || this.suitableFor.isEmpty()) {
			panel.add(new JLabel());
		} else {
			JPanel taskForThemPanel = new JPanel(new GridLayout(this.suitableFor.size(), 0));
			this.suitableFor.stream()
					.map(i -> new JLabel(i.getName()))
					.forEach(taskForThemPanel::add);
			panel.add(taskForThemPanel);
		}
		if ((this.rooms == null) || this.rooms.isEmpty()) {
			panel.add(new JLabel());
		} else {
			JPanel roomPanel = new JPanel(new GridLayout(this.rooms.size(), 0));
			this.rooms.stream()
					.map(r -> new JLabel(r.getName()))
					.forEach(roomPanel::add);
			panel.add(roomPanel);
		}
		return panel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o instanceof Task) { return this.taskID.equals(((Task) o).taskID); }
		return false;
	}

	@Override
	public int hashCode() {
		return this.taskID.hashCode();
	}

	@Override
	public int compareTo(Task o) {
		return this.nextDueDate.compareTo(o.nextDueDate);
	}
}
