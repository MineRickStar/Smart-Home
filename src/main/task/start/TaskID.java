package start;

import java.util.UUID;

/**
 * The Class TaskID.
 *
 * @author MineRickStar
 */
public class TaskID {

	/** The id. */
	private UUID ID;

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