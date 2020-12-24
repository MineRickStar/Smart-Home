package tasks;

/**
 * The Class Room.
 *
 * @author MineRickStar
 */
public enum Room {

	// ----- Keller -----

	/** The waschraum. */
	WASCHRAUM(Floor.KELLER, "Waschraum", "Wäsche und weiteres"),
	/** The bad keller. */
	BAD_KELLER(Floor.KELLER, "Bad Keller", "Gästebad im Keller"),
	/** The gaestezimmer. */
	GAESTEZIMMER(Floor.KELLER, "Gästezimmer"),
	/** The heizungsraum. */
	HEIZUNGSRAUM(Floor.KELLER, "Heizungsraum", "Heizung, Boiler und Werkstatt"),
	/** The vorratsraum. */
	VORRATSRAUM(Floor.KELLER, "Vorratsraum", "Alle Vorräte"),
	/** The treppenstauraum. */
	TREPPENSTAURAUM(Floor.KELLER, "Stauraum unter Treppe"),
	/** The kellerflur. */
	KELLERFLUR(Floor.KELLER, "Flur im Keller"),

	/** The kueche. */
	KUECHE(Floor.ERDGESCHOSS, "Küche", "Küche"),
	/** The flur. */
	FLUR(Floor.ERDGESCHOSS, "Flur", "Flur und Eingangsbereich"),;

	/** The floor. */
	private final Floor floor;

	/** The name. */
	private final String name;

	/** The description. */
	private final String description;

	/**
	 * Instantiates a new room.
	 *
	 * @param floor the floor
	 * @param name  the name
	 */
	Room(Floor floor, String name) {
		this.floor = floor;
		this.name = name;
		this.description = name;
	}

	/**
	 * Instantiates a new room.
	 *
	 * @param floor       the floor
	 * @param name        the name
	 * @param description the description
	 */
	Room(Floor floor, String name, String description) {
		this.floor = floor;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

}