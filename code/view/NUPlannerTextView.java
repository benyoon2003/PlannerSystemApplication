package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.IFeatures;
import model.Day;
import model.Event;
import model.IEvent;
import model.ReadOnlyPlannerModel;
import model.User;
import model.Utils;

/**
 * This is the text version of the view of a planner model.
 */
public class NUPlannerTextView implements PlannerView {

  private final ReadOnlyPlannerModel model;
  private String host;

  /**
   * Constructs a textual view version.
   *
   * @param model a ReadOnlyPlannerModel
   */
  public NUPlannerTextView(ReadOnlyPlannerModel model) {
    this.model = model;
    this.host = this.model.getListOfUser().get(0).toString();
  }


  /**
   * This creates a string of the events on a given day.
   *
   * @param user with the events being displayed.
   * @param day  the day on which the event s are being pulled.
   * @return the display of the events
   */
  private String daySchedule(String user, Day day) {
    String output = "";
    for (IEvent e : this.model.scheduleOnDay(user, day)) {
      output += e.toString();
    }
    return output;
  }

  @Override
  public String toString() {
    String output = "";
    for (User user : model.getListOfUser()) {
      output += "User: " + user.toString() + "\n";
      output += "Sunday: \n" + daySchedule(user.toString(), Day.Sunday);
      output += "Monday: \n" + daySchedule(user.toString(), Day.Monday);
      output += "Tuesday: \n" + daySchedule(user.toString(), Day.Tuesday);
      output += "Wednesday: \n" + daySchedule(user.toString(), Day.Wednesday);
      output += "Thursday: \n" + daySchedule(user.toString(), Day.Thursday);
      output += "Friday: \n" + daySchedule(user.toString(), Day.Friday);
      output += "Saturday: \n" + daySchedule(user.toString(), Day.Saturday);
    }
    return output;
  }

  /**
   * Produces a String displaying the current user and the schedule of that user.
   *
   * @return a String
   * @implNote This method is not provided in the interface as it is
   *           specific to this implementation of PlannerView.
   */
  public String displayUserSchedule() {
    String output = "";
    output += "User: " + host.toString() + "\n";
    output += "Sunday: \n" + daySchedule(host.toString(), Day.Sunday);
    output += "Monday: \n" + daySchedule(host.toString(), Day.Monday);
    output += "Tuesday: \n" + daySchedule(host.toString(), Day.Tuesday);
    output += "Wednesday: \n" + daySchedule(host.toString(), Day.Wednesday);
    output += "Thursday: \n" + daySchedule(host.toString(), Day.Thursday);
    output += "Friday: \n" + daySchedule(host.toString(), Day.Friday);
    output += "Saturday: \n" + daySchedule(host.toString(), Day.Saturday);
    return output;
  }

  @Override
  public void render(IEvent event) {
    System.out.println(displayUserSchedule());
  }

  @Override
  public void addFeatures(IFeatures features) {
    // Unimplemented
  }

  @Override
  public void reMakeView(String selectedUser, IFeatures features) {
    this.host = selectedUser;
    System.out.println(displayUserSchedule());
  }

  @Override
  public void showError(String msg) {
    // Unimplemented
  }
}
