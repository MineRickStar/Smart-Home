package start;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import user.Inhabitant;

/**
 * The Class Task.
 */
public class Task {

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

	/** The presumable finisher. */
	private ArrayList<Inhabitant> presumableFinishers;

	/** The finished. */
	private final HashMap<Inhabitant, List<LocalDateTime>> finished;

	/** The rooms. */
	private ArrayList<Room> rooms;

	/**
	 * Instantiates a new task.
	 *
	 * @param name        the name
	 * @param createDate  the create date
	 * @param nextDueDate the next due date
	 * @param period      the period
	 * @param rooms       the rooms
	 */
	public Task(String name, LocalDateTime createDate, LocalDateTime nextDueDate, Period period,
			ArrayList<Room> rooms) {
		this.taskID = TaskID.getRandomID();
		this.name = name;
		this.createDate = createDate;
		this.nextDueDate = nextDueDate;
		this.period = period;
		this.finished = new HashMap<>();
		this.rooms = rooms;
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
		this.nextDueDate = when.plus(this.period);
	}

	public LocalDateTime getNextDueDate() {
		return this.nextDueDate;
	}

	public ArrayList<Inhabitant> getPresumableFinishers() {
		return this.presumableFinishers;
	}

	/**
	 * Removes the presumable finisher.
	 *
	 * @param inhabitant the inhabitant
	 */
	public void removePresumableFinisher(Inhabitant inhabitant) {
		this.presumableFinishers.remove(inhabitant);
	}

	/**
	 * Adds the presumable finisher.
	 *
	 * @param inhabitant the inhabitant
	 */
	public void addPresumableFinisher(Inhabitant inhabitant) {
		if (!this.presumableFinishers.contains(inhabitant)) {
			this.presumableFinishers.add(inhabitant);
		}
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
}
