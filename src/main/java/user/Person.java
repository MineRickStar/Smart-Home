package user;

/**
 * The Class Person.
 *
 * @author MineRickStar
 */
public class Person {

	/** The person ID. */
	private final PersonID personID;

	/** The name. */
	protected String name;

	/**
	 * Instantiates a new person.
	 *
	 * @param name the name
	 */
	public Person(String name) {
		this.personID = PersonID.getRandomID();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o instanceof Person) { return this.personID.equals(((Person) o).personID); }
		return false;
	}

	@Override
	public int hashCode() {
		return this.personID.hashCode();
	}

}