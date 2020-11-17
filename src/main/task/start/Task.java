package start;

import java.time.LocalDateTime;

import user.Inhabitant;
import user.Person;

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

	/** The due date. */
	private LocalDateTime dueDate;

	/** The finished. */
	private LocalDateTime finished;

	/** The creator. */
	private final Person creator;

	/** The presumable finisher. */
	private Inhabitant presumableFinisher;

	/** The finisher. */
	private Inhabitant finisher;

	/**
	 * Instantiates a new task.
	 *
	 * @param name               the name
	 * @param createDate         the create date
	 * @param dueDate            the due date
	 * @param creator            the creator
	 * @param presumableFinisher the presumable finisher
	 */
	public Task(String name, LocalDateTime createDate, LocalDateTime dueDate, Person creator,
			Inhabitant presumableFinisher) {
		this.taskID = TaskID.getRandomID();
		this.name = name;
		this.createDate = createDate;
		this.dueDate = dueDate;
		this.creator = creator;
		this.presumableFinisher = presumableFinisher;
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
	 * Marks the Task as finishe, with given Inhabitant and the given endTime.
	 *
	 * @param when the when
	 * @param who  the who
	 */
	public void finished(LocalDateTime when, Inhabitant who) {
		this.finished = when;
		this.finisher = who;
	}

	public Inhabitant getPresumableFinisher() {
		return this.presumableFinisher;
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
