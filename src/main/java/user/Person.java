package user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class Person.
 *
 * @author MineRickStar
 */
public class Person {

	/** The Constant persons. */
	public static final List<Person> persons;

	static {
		persons = new ArrayList<>();
		Person.persons.add(new Inhabitant("Patrick"));
		Person.persons.add(new Inhabitant("Nicolette"));
		Person.persons.add(new Inhabitant("Thomas"));

	}

	/**
	 * Adds the person.
	 *
	 * @param person the person
	 */
	public static void addPerson(Person person) {
		if (!Person.persons.contains(person)) {
			Person.persons.add(person);
		}
	}

	public static List<Inhabitant> getInhabitants() {
		return Person.persons.stream()
				.filter(p -> p instanceof Inhabitant)
				.map(p -> (Inhabitant) p)
				.collect(Collectors.toList());
	}

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