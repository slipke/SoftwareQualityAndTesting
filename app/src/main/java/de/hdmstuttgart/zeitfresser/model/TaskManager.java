package de.hdmstuttgart.zeitfresser.model;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A TaskManager holds and administrates a list of tasks.
 */

public abstract class TaskManager {

  private List<Task> taskList = new LinkedList<>();

  TaskManager() {
    this.taskList = createTaskList();
  }

  protected abstract List<Task> createTaskList();

  /**
   * Return the current task list.
   *
   * @return the current {@link List} of tasks.
   */
  public List<Task> getTaskList() {
    return taskList;
  }

  /**
   * Start a task.
   *
   * @param task The task to be started.
   */
  public void startTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Argument \"task\" must not be null");
    }

    task.start();
  }

  /**
   * Stop a task. An {@link IllegalArgumentException} is thrown if {@code null} is passed as
   * argument.
   *
   * @param task The {@link Task} to be stopped.
   */
  public void stopTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Argument \"taskName\" must not be null or empty!)");
    }

    task.stop();
  }

  /**
   * Check if a certain {@link Task} is active.
   *
   * @param task The {@link Task} to be checked.
   * @return true if active, false otherwise.
   */
  public boolean isTaskActive(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Input argument \"task\" was null!");
    }

    return task.isActive();
  }

  /**
   * Compute total amount of time recorded for a certain {@code task}.
   *
   * @param task The task for which total time shall be computed.
   * @return The total amount of time over all records attached to {@code task}.
   */
  public float getOverallDurationForTask(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Input argument \"task\" was null!");
    }

    return task.getOverallDuration();
  }

  public List<Task> getFilteredTasks(Date from, Date to) {
    List<Task> copiedList = new LinkedList<>(taskList);

    if (from == null && to == null) {
      return filterZeroDurationTasks(copiedList);
    }

    if (from != null) {
      copiedList = filterTasksStartedBefore(copiedList, from);
    }

    if (to != null) {
      copiedList = filterTasksEndedAfter(copiedList, to);
    }

    return filterZeroDurationTasks(copiedList);
  }

  private List<Task> filterTasksStartedBefore(List<Task> tasks, Date start) {
    List<Task> filteredTasks = new LinkedList<>();

    for (Task task : tasks) {
      if (!task.hasRecordsBefore(start)) {
        filteredTasks.add(task);
      }
    }
    return filteredTasks;
  }


  private List<Task> filterTasksEndedAfter(List<Task> tasks, Date end) {
    List<Task> filteredTasks = new LinkedList<>();

    for (Task task : tasks) {
      if (!task.hasRecordsAfter(end)) {
        filteredTasks.add(task);
      }
    }
    return filteredTasks;
  }

  private List<Task> filterZeroDurationTasks(List<Task> tasks) {
    List<Task> filteredList = new LinkedList<>();

    for (Task task : tasks) {
      if (task.getOverallDuration() > 0.0f) {
        filteredList.add(task);
      }
    }

    return filteredList;
  }

  public List<Entry> taskListToEntryList(List<Task> tasks) {
    List<Entry> entries = new LinkedList<>();

    for (Task task : tasks) {
      entries.add(new Entry(task.getOverallDuration(), (int) task.getId()));
    }

    return entries;
  }

  public List<String> taskListToLabelList(List<Task> tasks) {
    List<String> labels = new LinkedList<>();

    for (Task task : tasks) {
      labels.add(task.getName());
    }

    return labels;
  }

}
