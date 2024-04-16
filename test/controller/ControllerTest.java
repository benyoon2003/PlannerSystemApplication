package controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Day;
import model.Event;
import model.IEvent;
import model.NuPlanner;
import model.PlannerModel;
import model.User;
import view.EventFrameView;
import view.EventView;
import view.NUPlannerTextView;
import view.ScheduleFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This is the testing suite for our NU planner controller. Due to how this controller interacts
 * with views there are some specific ways we had to test. Most tests utilize a text view to output
 * changes made to the model after calling commands on the controller. Other tests require
 * using the gui to input specifc values that cannot be passed without the gui. See tests notes.
 */
public class ControllerTest {

  private PlannerModel model;
  private NUPlannerTextView view;
  private NUPlannerController controller;
  private IEvent testEvent;
  private User ben;
  private User nico;
  private EventView ev;

  /**
   * This is a general setup for testing with one user and one event in the system.
   */
  private void exampleController() {
    this.model = new NuPlanner();
    ben = this.model.addUser("Ben");
    this.view = new NUPlannerTextView(this.model);
    String[] arr = {"Ben"};
    this.testEvent = this.model.createEvent("Ben", "OOD", "Snell", false,
            Day.Monday, 0, Day.Wednesday, 1000, List.of());
    this.controller = new NUPlannerController(this.model, new AnyTimeStrat(), this.view,
            new EventFrameView(this.testEvent, arr), ben);
  }

  /**
   * This setup is very similiar to the example controller but the event is almost non-existent
   * in the system which leave an almost empty system for testing.
   */
  private void exampleEmptyController() {
    this.model = new NuPlanner();
    ben = this.model.addUser("Ben");
    nico = this.model.addUser("Nico");
    this.view = new NUPlannerTextView(this.model);
    String[] arr = {"Ben"};
    this.testEvent = this.model.createEvent("Nico", "OOD", "Snell", false,
            Day.Sunday, 0, Day.Sunday, 1, List.of());
    this.controller = new NUPlannerController(this.model, new AnyTimeStrat(), this.view,
            new EventFrameView(arr, ben.toString()), ben);
  }

  /**
   * This is a setup specifically using the anytime strategy.
   */
  private void exampleSchedulingControllerAnytime() {
    this.model = new NuPlanner(new ArrayList<>());
    ben = this.model.addUser("Ben");
    nico = this.model.addUser("Nico");
    this.view = new NUPlannerTextView(this.model);
    String[] arr = {"Ben", "Nico"};
    this.ev = new ScheduleFrame("New", false,
            "Snell", "200", arr);
    this.testEvent = this.model.createEvent("Ben", "OOD", "Snell", false,
            Day.Monday, 0, Day.Wednesday, 1000, List.of());
    this.controller = new NUPlannerController(this.model, new AnyTimeStrat(), this.view,
            this.ev, ben);
  }

  /**
   * This is an example setup specifically using the Workday setup.
   */
  private void exampleSchedulingControllerWorkday() {
    exampleSchedulingControllerAnytime();
    nico = this.model.addUser("Nico");
    this.controller = new NUPlannerController(this.model, new WorkdayStrat(), this.view,
            this.ev, ben);
  }

  /**
   * This is an example setup using the lenient strategy.
   */
  private void exampleSchedulingControllerLenient() {
    exampleSchedulingControllerAnytime();
    this.controller = new NUPlannerController(this.model, new WorkdayStrat(), this.view,
            this.ev, ben);

    this.model.addUser("Nico");
    this.model.addUser("Lucia");
    this.model.createEvent("Lucia", "Lecture", "Churchill", true,
            Day.Tuesday, 0, Day.Friday, 1500, List.of());
    String[] arr = {"Ben", "Nico", "Lucia"};
    this.ev = new ScheduleFrame("New", false,
            "Snell", "200", arr);
  }


  /**
   * The following tests labeled setEventView test the event view that is created when selecting a
   * preexisting event. The three separate methods test that functionality is ensured no matter
   * the strategy.
   */
  @Test
  public void testSetEventViewAnytime() {
    exampleSchedulingControllerAnytime();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 0, Day.Sunday), "Set Event View");
    this.ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "New");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "Snell");
    assertEquals(ev.observeStartTimeFromEF(), 0);
    assertEquals(ev.observeStartDayFromEF(), Day.Sunday);
    assertEquals(ev.observeEndTimeFromEF(), 200);
    assertEquals(ev.observeEndDayFromEF(), Day.Sunday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of(ben.toString()));
  }

  @Test
  public void testSetEventViewWorkday() {
    exampleSchedulingControllerWorkday();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 1100, Day.Wednesday), "Set Event View");
    this.ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "New");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "Snell");
    assertEquals(ev.observeStartTimeFromEF(), 1100);
    assertEquals(ev.observeStartDayFromEF(), Day.Wednesday);
    assertEquals(ev.observeEndTimeFromEF(), 1300);
    assertEquals(ev.observeEndDayFromEF(), Day.Wednesday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of(ben.toString()));
  }

  @Test
  public void testSetEventViewLenient() {
    exampleSchedulingControllerLenient();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 1100, Day.Wednesday), "Set Event View");
    this.ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "New");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "Snell");
    assertEquals(ev.observeStartTimeFromEF(), 1100);
    assertEquals(ev.observeStartDayFromEF(), Day.Wednesday);
    assertEquals(ev.observeEndTimeFromEF(), 1300);
    assertEquals(ev.observeEndDayFromEF(), Day.Wednesday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of(ben.toString()));
  }

  /**
   * The following tests labeled CreateEventButton test that an empty frame view is created when
   * this action in performed and that the frame is un affected with different strategies. These
   * test then go on to make sure that the correct event eis created with all the correct data.
   */
  @Test
  public void testCreateEventButtonAnytime() {
    exampleSchedulingControllerAnytime();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    this.ev = new EventFrameView(new Event("Test Event", true, "NEU", Day.Thursday,
            "500", Day.Thursday, "600", List.of(nico)), new String[]{nico.toString(), ben.toString()}, ben.toString());
    this.controller.setEventView(this.ev);
    this.view.render(testEvent, "Create Event Button");
    String[] output0 = this.view.displayUserSchedule().split("\n");
    assertEquals(output0[25],"       time: Thursday: 500 -> Thursday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output1 = this.view.displayUserSchedule().split("\n");
    IEvent event = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event.observeHost(), ben);
    assertEquals(event.observeLocation(), "NEU");
    assertEquals(output1[7],"       time: Thursday: 500 -> Thursday: 600");
    assertEquals(event.observeInvitedUsers(), List.of(ben, nico));
  }

  @Test
  public void testCreateEventButtonWorkday() {
    exampleSchedulingControllerWorkday();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    this.ev = new EventFrameView("Test Event", true, "NEU", Day.Thursday,
            "500", Day.Thursday, "600", new String[]{ben.toString(),
            nico.toString()}, ben.toString(), new String[]{nico.toString()});
    this.controller.setEventView(this.ev);
    this.view.render(testEvent, "Create Event Button");
    String[] output2 = this.view.displayUserSchedule().split("\n");
    assertEquals(output2[25],"       time: Thursday: 500 -> Thursday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output3 = this.view.displayUserSchedule().split("\n");
    IEvent event1 = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event1.observeHost(), ben);
    assertEquals(event1.observeLocation(), "NEU");
    assertEquals(output3[7],"       time: Thursday: 500 -> Thursday: 600");
    assertEquals(event1.observeInvitedUsers(), List.of(ben, nico));
  }

  @Test
  public void testCreateEventButtonLenient() {
    exampleSchedulingControllerLenient();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    this.ev = new EventFrameView("Test Event", true, "NEU", Day.Thursday,
            "500", Day.Thursday, "600", new String[]{ben.toString(),
            nico.toString()}, ben.toString(), new String[]{nico.toString()});
    this.controller.setEventView(this.ev);
    this.view.render(testEvent, "Create Event Button");
    String[] output4 = this.view.displayUserSchedule().split("\n");
    assertEquals(output4[25],"       time: Thursday: 500 -> Thursday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output5 = this.view.displayUserSchedule().split("\n");
    IEvent event2 = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event2.observeHost(), ben);
    assertEquals(event2.observeLocation(), "NEU");
    assertEquals(output5[7],"       time: Thursday: 500 -> Thursday: 600");
    assertEquals(event2.observeInvitedUsers(), List.of(ben, nico));
  }

  /**
   * The following tests labeled ModifyEventButton tests that an event is correctly modified and
   * that the correct event frame view is shown with all the right information. The three
   * methods of test ensure that functionality is consistent with different strategies.
   */
  @Test
  public void testModifyEventButtonAnytime() {
    exampleSchedulingControllerAnytime();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 0, Day.Sunday), "Set Event View");
    ev = new EventFrameView("New", true, "NEU", Day.Thursday,
            "500", Day.Friday, "600", new String[]{ben.toString(), nico.toString()},
            ben.toString(), new String[]{nico.toString()});
    this.controller.setEventView(ev);
    this.view.render(testEvent, "Modify Event Button");
    String[] output0 = this.view.displayUserSchedule().split("\n");
    assertEquals(output0[19],"       time: Thursday: 500 -> Friday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output1 = this.view.displayUserSchedule().split("\n");
    assertEquals(output1[7], "       time: Thursday: 500 -> Friday: 600");
    IEvent event = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event.observeHost(), ben);
    assertEquals(event.observeLocation(), "NEU");
    assertEquals(event.observeInvitedUsers(), List.of(ben, nico));
    assertFalse(event.observeOnline());
  }

  @Test
  public void testModifyEventButtonWorkday() {
    exampleSchedulingControllerWorkday();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 1100, Day.Wednesday), "Set Event View");
    ev = new EventFrameView("New", true, "NEU", Day.Thursday,
            "500", Day.Friday, "600", new String[]{ben.toString(), nico.toString()},
            ben.toString(), new String[]{nico.toString()});
    this.controller.setEventView(ev);
    this.view.render(testEvent, "Modify Event Button");
    String[] output0 = this.view.displayUserSchedule().split("\n");
    assertEquals(output0[19],"       time: Thursday: 500 -> Friday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output1 = this.view.displayUserSchedule().split("\n");
    assertEquals(output1[7], "       time: Thursday: 500 -> Friday: 600");
    IEvent event = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event.observeHost(), ben);
    assertEquals(event.observeLocation(), "NEU");
    assertEquals(event.observeInvitedUsers(), List.of(ben, nico));
    assertFalse(event.observeOnline());
  }

  @Test
  public void testModifyEventButtonLenient() {
    exampleSchedulingControllerLenient();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(this.model.findEvent(ben.toString(), 1100, Day.Wednesday), "Set Event View");
    ev = new EventFrameView("New", true, "NEU", Day.Thursday,
            "500", Day.Friday, "600", new String[]{ben.toString(), nico.toString()},
            ben.toString(), new String[]{nico.toString()});
    this.controller.setEventView(ev);
    this.view.render(testEvent, "Modify Event Button");
    String[] output0 = this.view.displayUserSchedule().split("\n");
    assertEquals(output0[19],"       time: Thursday: 500 -> Friday: 600");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output1 = this.view.displayUserSchedule().split("\n");
    assertEquals(output1[7], "       time: Thursday: 500 -> Friday: 600");
    IEvent event = this.model.findEvent("Nico", 500, Day.Thursday);
    assertEquals(event.observeHost(), ben);
    assertEquals(event.observeLocation(), "NEU");
    assertEquals(event.observeInvitedUsers(), List.of(ben, nico));
    assertFalse(event.observeOnline());
  }

  /**
   * This test ensures that the remove event button works and correctly removes the right event.
   * This test is split into three section all showing that remove button works through all
   * strategies.
   */
  @Test
  public void testRemoveEventButton() {
    exampleSchedulingControllerAnytime();
    this.view.render(this.testEvent, "Set Event View");
    this.view.render(this.testEvent, "Remove Event Button");
    String[] output = this.view.displayUserSchedule().split("\n");
    //This assert equals makes user that once the event is deleted it no longer shows up
    // and the only text after Monday should be Tuesday
    assertEquals(output[3],"Tuesday: ");

    exampleSchedulingControllerWorkday();
    this.view.render(this.testEvent, "Set Event View");
    this.view.render(this.testEvent, "Remove Event Button");
    output = this.view.displayUserSchedule().split("\n");
    //This assert equals makes usre that once the event is deleted it no longer shows up
    // and the only text after Monday should be Tuesday
    assertEquals(output[3],"Tuesday: ");

    exampleSchedulingControllerLenient();
    this.view.render(this.testEvent, "Set Event View");
    this.view.render(this.testEvent, "Remove Event Button");
    output = this.view.displayUserSchedule().split("\n");
    //This assert equals makes usre that once the event is deleted it no longer shows up
    // and the only text after Monday should be Tuesday
    assertEquals(output[3],"Tuesday: ");
  }


  /**
   * This test makes sure the controller ad view are updated when a new user is selected.
   */
  @Test
  public void testSelectUserBox() {
    exampleEmptyController();
    this.view.render(testEvent, "");
    this.view.reMakeView(nico, this.controller);
    this.view.render(testEvent, "Select User Box");
    String[] output = this.view.displayUserSchedule().split("\n");
    assertEquals(output[0],"User: Nico");
  }

  /**
   * This test makes sure that the correct event frame view is created regardless of strategy.
   */
  @Test
  public void testCreateEventMainButtonAnytime() {
    exampleSchedulingControllerAnytime();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "");
    assertEquals(ev.observeStartTimeFromEF(), 0);
    assertEquals(ev.observeStartDayFromEF(), Day.Monday);
    assertEquals(ev.observeEndTimeFromEF(), 0);
    assertEquals(ev.observeEndDayFromEF(), Day.Monday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of());
  }

  @Test
  public void testCreateEventMainButtonWorkday() {
    exampleSchedulingControllerWorkday();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "");
    assertEquals(ev.observeStartTimeFromEF(), 0);
    assertEquals(ev.observeStartDayFromEF(), Day.Monday);
    assertEquals(ev.observeEndTimeFromEF(), 0);
    assertEquals(ev.observeEndDayFromEF(), Day.Monday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of());
  }

  @Test
  public void testCreateEventMainButtonLenient() {
    exampleSchedulingControllerLenient();
    this.view.render(testEvent, "Schedule Event Finish");
    this.view.render(testEvent, "Create Event Main Button");
    ev = this.controller.observeEventView();
    assertEquals(ev.observeEventNameFromEF(), "");
    assertFalse(ev.observeIsOnlineFromEF());
    assertEquals(ev.observeLocationFromEF(), "");
    assertEquals(ev.observeStartTimeFromEF(), 0);
    assertEquals(ev.observeStartDayFromEF(), Day.Monday);
    assertEquals(ev.observeEndTimeFromEF(), 0);
    assertEquals(ev.observeEndDayFromEF(), Day.Monday);
    assertEquals(ev.observeHostFromEF(), ben.toString());
    assertEquals(ev.observeAvailUsersFromEF(), List.of());
  }

  /**
   * This test makes sure that the correct empty schedule event frame is created when that action
   * is called.
   */
  @Test
  public void testScheduleEventButton() {
    exampleController();
    this.view.render(testEvent, "Schedule Event Button");
    this.ev = this.controller.observeEventView();
    assertEquals("", this.ev.observeEventNameFromEF());
    assertEquals(0, this.ev.observeDurationFromSF());
  }


  /**
   * For these test our add schedule command opens the JFILE chooser to obtain the path
   * For this test choose the Ben XML file. These tests are testing adding that file.
   * We did not seperate the Jfile chooser from the command itself as the logic that
   * does not use the Jfile chooser is three lines long and replacing this with a new class
   * is more work than the code being saved.
   */
  @Test
  public void testAddSchedule() {
    exampleEmptyController();
    this.view.render(testEvent, "");
    this.view.render(testEvent, "Add Menu Bar");
    String[] output = this.view.displayUserSchedule().split("\n");
    assertEquals(output[3], "       name: Working on OOD");
  }

  @Test (expected = ClassCastException.class)
  public void testAddScheduleFailed() {
    exampleController();
    this.view.render(testEvent, "");
    this.view.render(testEvent, "Add Menu Bar");
  }

  /**
   * These following test make sure that for every strategy and inputted values for
   * the schedule event frame that an event is created and viewed in the correct place
   * given which strategy is being tested.
   */
  @Test
  public void testScheduleEventAnytime() {
    exampleSchedulingControllerAnytime();
    this.view.render(testEvent, "");
    this.view.render(testEvent, "Schedule Event Finish");
    String[] output = this.view.displayUserSchedule().split("\n");
    assertEquals(output[3], "       time: Sunday: 0 -> Sunday: 200");
  }

  @Test
  public void testScheduleEventWorkHours() {
    exampleSchedulingControllerWorkday();
    this.view.render(testEvent, "");
    this.view.render(testEvent, "Schedule Event Finish");
    String[] output = this.view.displayUserSchedule().split("\n");
    assertEquals(output[18], "       time: Wednesday: 1100 -> Wednesday: 1300");
  }

  @Test
  public void testScheduleEventLenient() {
    exampleSchedulingControllerLenient();
    this.view.render(testEvent, "");
    this.view.render(testEvent, "Schedule Event Finish");
    String[] output = this.view.displayUserSchedule().split("\n");
    assertEquals(output[18], "       time: Wednesday: 1100 -> Wednesday: 1300");
  }

}
