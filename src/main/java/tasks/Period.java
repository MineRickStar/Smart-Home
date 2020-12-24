package tasks;

import java.util.Arrays;

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
	/** The each week. */
	EACH_WEEK("Jede Woche", java.time.Period.ofDays(7)),
	/** The each weekend. */
	EACH_WEEKEND("Jedes Wochenende", java.time.Period.ofDays(7)),
	/** The each month. */
	EACH_MONTH("Jeden Monat", java.time.Period.ofMonths(1)),
	/** The once. */
	ONCE("Einmalig", null),
	/** The when nessecary. */
	WHEN_NESSECARY("Wenn nötig", null);

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

	/**
	 * Parses the period.
	 *
	 * @param period the period
	 * @return the period
	 */
	public static Period parse(String period) {
		return Arrays.stream(Period.values())
				.filter(p -> p.getName()
						.equals(period))
				.findAny()
				.orElse(EACH_WEEK);
	}

	@Override
	public String toString() {
		return this.name;
	}

}