package model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This is the testing suite for the User class.
 */
public class UserTest {

  private User example;

  private Event one;

  private Event two;

  private void exampleSchedule() {
    one = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of());
    two = new Event("Host Event", "Snell", false,
            Day.Sunday, 1200, Day.Monday, 800, List.of());
    example = new User("Example", List.of(one, two));
  }

  private void exampleConflictSchedule() {
    one = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Thursday, 1800, List.of());
    two = new Event("Host Event", "Snell", false,
            Day.Monday, 1200, Day.Tuesday, 1800, List.of());
    example = new User("Conflict", List.of(one, two));
    one.setInvitedUsers(List.of(example));
    two.setInvitedUsers(List.of(example));
  }

  private void example2ConflictSchedule() {
    one = new Event("Host Event", "Snell", false,
            Day.Friday, 1000, Day.Thursday, 1800, List.of());
    two = new Event("Host Event", "Snell", false,
            Day.Saturday, 1200, Day.Saturday, 1800, List.of());
    example = new User("Conflict", List.of(one, two));
    one.setInvitedUsers(List.of(example));
    two.setInvitedUsers(List.of(example));
  }

  private void exampleAddEvent() {
    one = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of());
    two = new Event("Host Event", "Snell", false,
            Day.Sunday, 1200, Day.Sunday, 1800, List.of());
    example = new User("Example", List.of(one));
    one.setInvitedUsers(List.of(example));
    two.setInvitedUsers(List.of(example));
  }

  private void exampleAddEventInvalid() {
    one = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Thursday, 1800, List.of());
    two = new Event("Host Event", "Snell", false,
            Day.Monday, 1200, Day.Tuesday, 1800, List.of());
    example = new User("Conflict", List.of(one));
    one.setInvitedUsers(List.of(example));
    two.setInvitedUsers(List.of(example));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConflict() {
    exampleConflictSchedule();
  }

  @Test(expected = IllegalArgumentException.class)
  public void test2Conflict() {
    example2ConflictSchedule();
  }

  @Test
  public void testSortEvents() {
    exampleSchedule();
    assertEquals(this.example.observeSchedule().get(0), two);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent() {
    exampleAddEvent();
    this.example.addEvent(two);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddEvent() {
    exampleAddEventInvalid();
    this.example.addEvent(two);
  }

  @Test
  public void testEventOnDay() {
    exampleSchedule();
    assertEquals(this.example.eventsOnDay(Day.Monday), List.of(two, one));
  }
}