package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This is the testing suite for our NUPlanner model. This tests all public functions and
 * some edge cases.
 */
//TODO: Test all public methods
public class NuPlannerTest {

  private PlannerModel example;
  private PlannerModel example2;

  private IUser ben;

  private IUser nico;

  private IUser lucia;
  private IUser patrick;
  private IUser spongebob;
  private IUser squidward;

  private IEvent e1;

  private void exampleNuPlanner() {
    this.example = new NuPlanner(new ArrayList<IUser>());
    ben = this.example.addUser("Ben");
    nico = this.example.addUser("Nico");
    e1 = this.example.createEvent("Ben", "Working on OOD", "Snell", false,
            Day.Monday, 2000, Day.Thursday, 2059, List.of("Nico"));
  }

  private void exampleNuPlanner2() {
    this.example2 = new NuPlanner(new ArrayList<>());
    lucia = this.example2.addUser("Lucia");
    patrick = this.example2.addUser("Patrick");
    spongebob = this.example2.addUser("Spongebob");
    squidward = this.example2.addUser("Squidward");
    IEvent e2 = this.example2.createEvent("Lucia", "grading exams", "home", true,
            Day.Monday, 0, Day.Monday, 1, List.of("Squidward"));
    IEvent e3 = this.example2.createEvent("Patrick", "eating", "Krusty Krab", false,
            Day.Tuesday, 500, Day.Thursday, 10, List.of("Squidward", "Spongebob"));
    IEvent e4 = this.example2.createEvent("Spongebob", "flipping patties", "Krusty Krab",
            false, Day.Friday, 600, Day.Saturday, 700, List.of("Patrick"));
  }

  @Test
  public void testUploadSchedule() {
    exampleNuPlanner();
    // Tests uploading an XML file into the database
    User jon = new User("Jon", List.of());
    Utils.writeToFile(jon, "");
    example.uploadSchedule("Jon");
    assertEquals(Utils.findUser("Jon", example.getListOfUser()), jon);

    // Tests uploading an XML file that contains a list of invitees that does not
    // already exist in the database
    try {
      example.uploadSchedule("Lucia");
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void testSaveSchedule() {
    exampleNuPlanner();
    example.saveSchedule();

    assertEquals(Utils.readXML("Ben", example.getListOfUser()), ben);
    assertEquals(Utils.readXML("Nico", example.getListOfUser()), nico);

    exampleNuPlanner2();
    example2.saveSchedule();

    assertEquals(Utils.readXML("Lucia", example2.getListOfUser()), lucia);
    assertEquals(Utils.readXML("Spongebob", example2.getListOfUser()), spongebob);
    assertEquals(Utils.readXML("Patrick", example2.getListOfUser()), patrick);
    assertEquals(Utils.readXML("Squidward", example2.getListOfUser()), squidward);
  }

  @Test
  public void testSelectSchedule() {
    exampleNuPlanner();
    assertEquals(example.selectSchedule("Ben"), List.of(e1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSelectScheduleException() {
    exampleNuPlanner();
    assertEquals(example.selectSchedule("Lucia"), List.of(e1));
  }

  @Test
  public void testCreateEvent() {
    exampleNuPlanner();
    IEvent e2 = this.example.createEvent("Ben", "OOD", "Snell", true
            , Day.Friday, 1800, Day.Saturday, 1800, List.of("Nico"));
    assertTrue(ben.observeSchedule().contains(e2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateEventButEventConflictsWithHost() {
    exampleNuPlanner();
    this.example.createEvent("Ben", "OOD", "Snell", true
            , Day.Wednesday, 1800, Day.Saturday, 1800, List.of("Nico"));
  }

  @Test
  public void testEventCreatedButConflictsWithOtherUser() {
    exampleNuPlanner2();
    IEvent e5 = this.example2.createEvent("Lucia", "OOD", "Snell", true
            , Day.Wednesday, 1800, Day.Wednesday, 2000, List.of("Patrick"));
    assertFalse(this.patrick.observeSchedule().contains(e5));
    //makes sure that the event is not removed from the host's schedule.
    assertTrue(this.lucia.observeSchedule().contains(e5));
  }

  @Test
  public void testRemoveEventAsHost() {
    exampleNuPlanner();
    this.example.removeEvent("Ben", e1);
    assertFalse(this.nico.observeSchedule().contains(e1));
  }

  @Test
  public void testRemoveEventAsAttendee() {
    exampleNuPlanner();
    this.example.removeEvent("Nico", e1);
    assertTrue(this.ben.observeSchedule().contains(e1));
  }

  @Test
  public void testModifyEvent() {
    exampleNuPlanner();
    this.example.modifyEvent(e1, "OOD", "Snell", false
            , Day.Wednesday, 1000, Day.Wednesday, 1200, List.of(), "Ben");
    assertEquals(this.example.findEvent(ben.toString(), 1000, Day.Wednesday), e1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalModifyEvent() {
    exampleNuPlanner();
    this.example.modifyEvent(e1, "OOD", "Snell", false
            , Day.Wednesday, -1000, Day.Wednesday, 1200, List.of(), "Ben");
  }

  @Test
  public void testEventAtThisTime() {
    exampleNuPlanner();
    assertEquals(this.example.eventsAtThisTime("Ben", 2000), List.of(e1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEventAtThisTimeWithInvalidTime() {
    exampleNuPlanner();
    this.example.eventsAtThisTime("Ben", 1000);
  }

  @Test
  public void testAddUser() {
    exampleNuPlanner();
    // Tests if given uid is already in system
    try {
      example.addUser("Ben");
    } catch (IllegalArgumentException ignored) {
    }

    // Tests if given uid is not in system
    example.addUser("Lucia");
    assertEquals(Utils.findUser("Lucia", example.getListOfUser()),
            new User("Lucia", List.of()));
  }

  @Test
  public void testAddUserWithExistingUser() {
    exampleNuPlanner();
    PlannerModel copyOfExample = example;
    Event conflictedGolf = new Event("Golf", "course", false,
            Day.Monday, 2000, Day.Thursday, 2059, List.of());
    Event unconflictedGolf = new Event("Golf", "course", false,
            Day.Friday, 2000, Day.Friday, 2059, List.of());
    User conflictedBen = new User("Ben", List.of(conflictedGolf));
    User unconflictedBen = new User("Ben", List.of(unconflictedGolf));
    User hunter = new User("Hunter", List.of());

    // Tests if the given user contains a conflicting schedule with pre-existing user
    try {
      example.addUser(conflictedBen);
    }
    catch (IllegalArgumentException ignored) {
    }

    // Tests if the given user does not contain a conflicting schedule
    example.addUser(unconflictedBen);
    assertEquals(example.scheduleOnDay("Ben", Day.Friday), List.of(unconflictedGolf));

    // Tests if the given user does not exist in the database
    example.addUser(hunter);
    assertEquals(Utils.findUser("Hunter", example.getListOfUser()),
            new User("Hunter", List.of()));
  }

  @Test
  public void testScheduleOnDay() {
    exampleNuPlanner();
    assertEquals(this.example.scheduleOnDay("Ben", Day.Monday), List.of(e1));
  }

  @Test
  public void testScheduleOnDayWithNoEvents() {
    exampleNuPlanner();
    assertEquals(this.example.scheduleOnDay("Ben", Day.Friday), List.of());
  }


}
