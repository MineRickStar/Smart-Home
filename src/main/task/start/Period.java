package start;

/**
 * The Class Period.
 *
 * @author MineRickStar
 */
public enum Period {

	/** The every day. */
	EVERY_DAY("Jeden Tag", java.time.Period.ofDays(1)),
	/** The every 2 day. */
	EVERY_2_DAY("Jeden 2 Tag", java.time.Period.ofDays(2)),
	/** The Constant EACH_WEEK. */
	EACH_WEEK("Jede Woche", java.time.Period.ofDays(7)),
	/** The each weekend. */
	EACH_WEEKEND("Jede Wochenende", java.time.Period.ofDays(7)),
	/** The each month. */
	EACH_MONTH("Jeden Monat", java.time.Period.ofMonths(1)),
	/** The once. */
	ONCE("Einmalig", null),;

	/** The name. */
	private String name;

	/** The period. */
	private java.time.Period period;

	/**
	 * Instantiates a new period.
	 *
	 * @param name   the name
	 * @param period the period
	 */
	Period(String name, java.time.Period period) {
		this.name = name;
		this.period = period;
	}

	public String getName() {
		return this.name;
	}

	public java.time.Period getJavaPeriod() {
		return this.period;
	}

}