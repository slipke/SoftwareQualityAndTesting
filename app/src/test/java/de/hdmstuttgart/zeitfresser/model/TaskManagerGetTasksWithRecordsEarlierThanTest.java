package de.hdmstuttgart.zeitfresser.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by patrick on 22.02.17.
 */

public class TaskManagerGetTasksWithRecordsEarlierThanTest extends TaskManagerBaseTest {

  private Method getTasksWithRecordsEarlierThan;

  @Before
  public void setUp() throws Exception {
    getTasksWithRecordsEarlierThan = TaskManager.class.getDeclaredMethod
        ("getTasksWithRecordsEarlierThan", Date.class, List.class);

    if (getTasksWithRecordsEarlierThan != null) {
      getTasksWithRecordsEarlierThan.setAccessible(true);
    }
  }


  @Test
  public void testGetTasksWithRecordsEarlierThanThrowsExceptionOnNullArgs() throws Exception {
    // TODO Split up into several test cases.
    try {
      getTasksWithRecordsEarlierThan.invoke(this, null, new LinkedList<>());
      fail("An exception should have been thrown.");
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause().getClass().equals(IllegalArgumentException.class), equalTo(true));
      assertThat(ex.getCause().getMessage(), equalTo("Argument 'date' must not be null!"));
    }

    try {
      getTasksWithRecordsEarlierThan.invoke(this, new Date(), null);
      fail("An exception should have been thrown.");
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause().getClass().equals(IllegalArgumentException.class), equalTo(true));
      assertThat(ex.getCause().getMessage(), equalTo("Argument 'tasks' must not be null!"));
    }

    try {
      getTasksWithRecordsEarlierThan.invoke(this, null, null);
      fail("An exception should have been thrown.");
    } catch (InvocationTargetException ex) {
      assertThat(ex.getCause().getClass().equals(IllegalArgumentException.class), equalTo(true));
      assertThat(ex.getCause().getMessage(), equalTo("Argument 'date' must not be null!"));
    }
  }


  @Test
  public void testGetTasksWithRecordsEarlierThanReturnsEmptyList() throws Exception {
    Object result = getTasksWithRecordsEarlierThan.invoke(this, new Date(), new LinkedList<>());

    assertThat(result, notNullValue());
    assertThat(result.getClass().equals(LinkedList.class), equalTo(true));
    assertThat(((LinkedList) result).isEmpty(), equalTo(true));
  }

  @Test
  public void testGetTasksWithRecordsEarlierThanFiltersProperly() throws Exception {
    Object result = getTasksWithRecordsEarlierThan.invoke(this, testDate, getTaskList());

    assertThat(result, notNullValue());
    assertThat(result.getClass().equals(LinkedList.class), equalTo(true));
    assertThat(((LinkedList) result).size(), equalTo(2));
  }

}