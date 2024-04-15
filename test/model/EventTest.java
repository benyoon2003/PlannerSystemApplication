package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Day;
import model.Event;
import model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * This is the testing suite for the event class.
 */
public class EventTest {

  private Event example;


  private User user1;

  private User user2;

  private void exampleInvalidTimes() {
    this.user1 = new User("User1", new ArrayList<>());
    this.example = new Event("Invalid Event", "Snell", false,
            Day.Monday, 1800, Day.Monday, 1800, List.of(user1));
  }

  private void exampleInvalidStartTimes() {
    this.user1 = new User("User1", new ArrayList<>());
    this.example = new Event("Invalid Event", "Snell", false,
            Day.Monday, -1800, Day.Tuesday, 1800, List.of(user1));
  }

  private void exampleInvalidEndTimes() {
    this.user1 = new User("User1", new ArrayList<>());
    this.example = new Event("Invalid Event", "Snell", false,
            Day.Monday, 1800, Day.Tuesday, 3600, List.of(user1));
  }

  private void exampleHostUser() {
    this.user1 = new User("User1", new ArrayList<>());
    this.example = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of(user1));
  }

  private void exampleHostUserEvent() {
    this.user1 = new User("User1", new ArrayList<>());
    this.user2 = new User("User 2", new ArrayList<>());
    this.example = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of(user1, user2));
    this.example.sendInvite();
  }

  private void exampleHostUserEventConflict() {
    this.user1 = new User("User1", new ArrayList<>());
    this.user2 = new User("User 2", new ArrayList<>());
    this.example = new Event("Host Event", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of(user1, user2));
    Event event2 = new Event("Event 2", "Snell", false,
            Day.Monday, 1000, Day.Tuesday, 1800, List.of(user2));
    event2.sendInvite();
    this.example.sendInvite();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTimes() {
    exampleInvalidTimes();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartTimes() {
    exampleInvalidStartTimes();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEndTimes() {
    exampleInvalidEndTimes();
  }

  @Test
  public void testHostUser() {
    exampleHostUser();
    assertEquals(example.observeHost(), user1);
  }

  @Test
  public void testSendInvite() {
    exampleHostUserEvent();
    assertTrue(user2.observeSchedule().contains(this.example));
  }

  @Test
  public void testSendInviteWithConflict() {
    exampleHostUserEventConflict();
    assertFalse(user2.observeSchedule().contains(this.example));
  }

}