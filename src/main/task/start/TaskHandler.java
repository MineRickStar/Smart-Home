package start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		this.readCSV();
	}

	/** The counter for unnamed Tasks. */
	private static int counter = 0;

	/**
	 * Adds a task with a name of the form "Task-#".
	 *
	 * @param nextDueDate  the next due date
	 * @param period       the period
	 * @param whenToFinish the when to finish
	 * @param rooms        the rooms
	 * @return the task
	 */
	public Task createTask(LocalDateTime nextDueDate, Period period, Time whenToFinish, ArrayList<Room> rooms) {
		return this.createTask("Task-" + TaskHandler.counter++, nextDueDate, period, whenToFinish, rooms);
	}

	/**
	 * Adds a task with the given name and now as the createTime.
	 *
	 * @param name         the name
	 * @param nextDueDate  the next due date
	 * @param period       the period
	 * @param whenToFinish the when to finish
	 * @param rooms        the rooms
	 * @return the task
	 */
	public Task createTask(String name, LocalDateTime nextDueDate, Period period, Time whenToFinish,
			ArrayList<Room> rooms) {
		Task task = new Task(name, LocalDateTime.now(), nextDueDate, period, whenToFinish, rooms);
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
				.filter(task -> {
					List<Inhabitant> presumableFinishers = task.getPresumableFinishers();
					if (presumableFinishers == null) { return false; }
					return presumableFinishers.contains(inhabitant);
				})
				.collect(Collectors.toList());
	}

	public List<Task> getAllTasks() {
		return this.tasks;
	}

	/**
	 * Read CSV.
	 */
	private void readCSV() {
		File csv = new File("Tasks.csv");
		if (!csv.exists()) { return; }
		try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
			String firstLine = reader.readLine();
			reader.lines()
					.map(Task::new)
					.forEach(this.tasks::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write CSV.
	 */
	public void writeCSV() {
		try (FileWriter writer = new FileWriter(new File("Tasks.csv"))) {
			writer.append(Task.getCSVFields())
					.append(System.lineSeparator());
			this.tasks.forEach(task -> {
				try {
					writer.append(task.toCSVString())
							.append(System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}