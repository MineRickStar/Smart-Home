package start;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import user.Inhabitant;
import user.Person;

/**
 * The Class TaskHandler.
 *
 * @author MineRickStar
 */
public class TaskHandler {

	/** The instance. */
	private static TaskHandler instance = new TaskHandler();

	public static TaskHandler getInstance() {
		return TaskHandler.instance;
	}

	/** The tasks. */
	private final ArrayList<Task> tasks;

	/**
	 * Instantiates a new task handler.
	 */
	private TaskHandler() {
		this.tasks = new ArrayList<>();
	}

	/** The counter for unnamed Tasks. */
	private static int counter = 0;

	/**
	 * Adds a task with a name of the form "Task-#".
	 *
	 * @param dueDate            the due date
	 * @param creator            the creator
	 * @param presumableFinisher the presumable finisher
	 * @return the task
	 */
	public Task createTask(LocalDateTime dueDate, Person creator, Inhabitant presumableFinisher) {
		return this.createTask("Task-" + TaskHandler.counter++, dueDate, creator, presumableFinisher);
	}

	/**
	 * Adds a task with the given name and now as the creatTime.
	 *
	 * @param name               the name
	 * @param dueDate            the due date
	 * @param creator            the creator
	 * @param presumableFinisher the presumable finisher
	 * @return the task
	 */
	public Task createTask(String name, LocalDateTime dueDate, Person creator, Inhabitant presumableFinisher) {
		Task task = new Task(name, LocalDateTime.now(), dueDate, creator, presumableFinisher);
		this.tasks.add(task);
		return task;
	}

	/**
	 * Gets the tasks for the given Inhabitant.
	 *
	 * @param inhabitant the inhabitant
	 * @return the tasks for
	 */
	public List<Task> getTasksFor(Inhabitant inhabitant) {
		return this.tasks.parallelStream()
				.filter(task -> task.getPresumableFinisher()
						.equals(inhabitant))
				.collect(Collectors.toList());
	}

	public List<Task> getAllTasks() {
		return this.tasks;
	}

}