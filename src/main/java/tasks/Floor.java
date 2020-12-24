package tasks;

/**
 * The Enum Floor.
 *
 * @author MineRickStar
 */
public enum Floor {

	/** The keller. */
	KELLER("Keller"),
	/** The erdgeschoss. */
	ERDGESCHOSS("Erdgeschoss"),
	/** The stock. */
	ERSTER_STOCK("1. Stock"),
	/** The dachboden. */
	DACHBODEN("Dachboden"),;

	/** The name. */
	public final String name;

	/**
	 * Instantiates a new floor.
	 *
	 * @param name the name
	 */
	Floor(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}