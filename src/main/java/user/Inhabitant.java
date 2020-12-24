package user;

import java.util.Arrays;

/**
 * The Enum Inhabitant.
 *
 * @author MineRickStar
 */
public enum Inhabitant {

	/** The patrick. */
	PATRICK("Patrick"),
	/** The nicolette. */
	NICOLETTE("Nicolette"),
	/** The thomas. */
	THOMAS("Thomas"),
	/** The other. */
	OTHER("Jemand anderes", false);

	/** The Constant name. */
	public final String name;

	/** The default user. */
	public final boolean defaultUser;

	/**
	 * Instantiates a new inhabitant.
	 *
	 * @param name the name
	 */
	Inhabitant(String name) {
		this(name, true);
	}

	/**
	 * Instantiates a new inhabitant.
	 *
	 * @param name        the name
	 * @param defaultUser the default user
	 */
	Inhabitant(String name, boolean defaultUser) {
		this.name = name;
		this.defaultUser = defaultUser;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Gets the inhabitant from name.
	 *
	 * @param name the name
	 * @return the inhabitant from name
	 */
	public static Inhabitant getInhabitantFromName(String name) {
		return Arrays.stream(Inhabitant.values())
				.filter(i -> i.getName()
						.equals(name))
				.findAny()
				.orElse(null);
	}

}