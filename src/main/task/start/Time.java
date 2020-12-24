package start;

import java.time.LocalTime;

/**
 * The Class Time.
 *
 * @author MineRickStar
 */
public enum Time {

	/** The Constant AT_THE_DAY. */
	AT_THE_DAY("Am Tag", LocalTime.MIN, LocalTime.MAX),
	/** The in the morning. */
	IN_THE_MORNING("In der Früh", LocalTime.of(7, 0), LocalTime.of(10, 00)),
	/** The forenoon. */
	FORENOON("Am Vormittag", LocalTime.of(10, 0), LocalTime.NOON),
	/** The noon. */
	NOON("Am Mittag", LocalTime.of(11, 0), LocalTime.of(13, 00)),
	/** The afternoon. */
	AFTERNOON("Am Nachmittag", LocalTime.NOON, LocalTime.of(15, 00)),
	/** The evening. */
	EVENING("Am Abend", LocalTime.of(15, 00), LocalTime.of(20, 00)),;

	/** The display name. */
	private String displayName;

	/** The until. */
	private LocalTime from, until;

	/**
	 * Instantiates a new time.
	 *
	 * @param displayName the display name
	 * @param fromTo      the from to
	 */
	Time(String displayName, LocalTime fromTo) {
		this.displayName = displayName;
		this.from = fromTo;
		this.until = fromTo;
	}

	/**
	 * Instantiates a new time.
	 *
	 * @param displayName the display name
	 * @param from        the from
	 * @param until       the until
	 */
	Time(String displayName, LocalTime from, LocalTime until) {
		this.displayName = displayName;
		this.from = from;
		this.until = until;
	}

	public String getDisplayName() {
		return this.displayName;
	}

}