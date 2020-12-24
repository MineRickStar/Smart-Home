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

	// ----- Erdgeschoss -----

	/** The kueche. */
	KUECHE(Floor.ERDGESCHOSS, "Küche"),
	/** The flur. */
	FLUR_ERDGESCHOSS(Floor.ERDGESCHOSS, "Flur Erdgeschoss", "Flur und Eingangsbereich"),
	/** The essecke. */
	ESSECKE(Floor.ERDGESCHOSS, "Essecke"),
	/** The eingangbereich. */
	EINGANGBEREICH(Floor.ERDGESCHOSS, "Eingangsbereich"),
	/** The buero. */
	BUERO(Floor.ERDGESCHOSS, "Büro"),
	/** The wohnzimmer. */
	WOHNZIMMER(Floor.ERDGESCHOSS, "Wohnzimmer"),
	/** The bad erdgeschoss. */
	BAD_ERDGESCHOSS(Floor.ERDGESCHOSS, "Bad Erdgeschoss"),

	// ----- 1.Stock -----

	/** The bad erster stok. */
	BAD_ERSTER_STOK(Floor.ERSTER_STOCK, "Bad 1 Stock"),
	/** The flur erster stock. */
	FLUR_ERSTER_STOCK(Floor.ERSTER_STOCK, "Flur 1 Stock"),
	/** The patricks zimmer. */
	PATRICKS_ZIMMER(Floor.ERSTER_STOCK, "Patricks Zimmer"),
	/** The schlafzimmer. */
	SCHLAFZIMMER(Floor.ERSTER_STOCK, "Schlafzimmer"),
	/** The mamas zimmer. */
	MAMAS_ZIMMER(Floor.ERSTER_STOCK, "Mamas Zimmer"),;

	/** The floor. */
	public final Floor floor;

	/** The name. */
	public final String name;

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
}