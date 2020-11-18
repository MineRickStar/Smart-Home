package start;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import user.Inhabitant;

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
	 * @param nextDueDate the next due date
	 * @param period      the period
	 * @param rooms       the rooms
	 * @return the task
	 */
	public Task createTask(LocalDateTime nextDueDate, Period period, ArrayList<Room> rooms) {
		return this.createTask("Task-" + TaskHandler.counter++, nextDueDate, period, rooms);
	}

	/**
	 * Adds a task with the given name and now as the createTime.
	 *
	 * @param name        the name
	 * @param nextDueDate the next due date
	 * @param period      the period
	 * @param rooms       the rooms
	 * @return the task
	 */
	public Task createTask(String name, LocalDateTime nextDueDate, Period period, ArrayList<Room> rooms) {
		Task task = new Task(name, LocalDateTime.now(), nextDueDate, period, rooms);
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
				.filter(task -> task.getPresumableFinishers()
						.contains(inhabitant))
				.collect(Collectors.toList());
	}

	public List<Task> getAllTasks() {
		return this.tasks;
	}

}