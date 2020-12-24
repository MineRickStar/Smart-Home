package tasks;

import java.util.UUID;

/**
 * The Class TaskID.
 *
 * @author MineRickStar
 */
public class TaskID {

	/** The id. */
	public final UUID ID;

	/**
	 * Instantiates a new task ID.
	 *
	 * @param ID the id
	 */
	private TaskID(UUID ID) {
		this.ID = ID;
	}

	public static TaskID getRandomID() {
		return new TaskID(UUID.randomUUID());
	}

	/**
	 * Returns a new TaskID from the given String.
	 *
	 * @param ID the id
	 * @return the task ID
	 */
	public static TaskID of(String ID) {
		return new TaskID(UUID.fromString(ID));
	}

	@Override
	public String toString() {
		return this.ID.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (o instanceof TaskID) { return this.ID.equals(((TaskID) o).ID); }
		return false;
	}

	@Override
	public int hashCode() {
		return this.ID.hashCode();
	}
}