package user;

import java.util.UUID;

/**
 * The Class PersonID.
 *
 * @author MineRickStar
 */
public class PersonID {

	/** The id. */
	private final UUID ID;

	/**
	 * Instantiates a new person ID.
	 *
	 * @param ID the id
	 */
	private PersonID(UUID ID) {
		this.ID = ID;
	}

	public static PersonID getRandomID() {
		return new PersonID(UUID.randomUUID());
	}

	@Override
	public int hashCode() {
		return this.ID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj instanceof PersonID) { return this.ID.equals(((PersonID) obj).ID); }
		return false;
	}

}
