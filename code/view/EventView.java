package view;


import java.awt.event.ActionListener;
import java.util.List;

import controller.IFeatures;
import model.Day;

/**
 * This interface should represent the functionality of the event frame which is the
 * Event dialogue box. This box is used to edit, modify, or create a new event and
 * allows the user to control their planner system. Implementations of this event frame
 * will connect to the controller to control the model but in this assignment they do not.
 */
public interface EventView {

  /**
   * This method adds the feature throughout the view. And allows the features to receive
   * the requests.
   * @param features IFeatures
   */
  void addFeatures(IFeatures features);

  /**
   * This method displays the event frame view where the event dialogue can be seen.
   */
  void display();

  /**
   * Closes the event frame after an action performed.
   */
  void close();

  /**
   * This method calls system.out to output the event details as specified
   * in the assignment for the user to see their details in the console or
   * some other output.
   */
  void outputEventDetails();

  /**
   * This method checks if the event view frame was left empty by the user,
   * before clicking the create event button making it an invalid event.
   *
   * @return boolean
   */
  boolean validInput();

  /**
   * Observes the inputted name parameter of the event by the user on the Event Frame.
   *
   * @return String of the event name
   */
  String observeEventNameFromEF();

  /**
   * Observes the inputted is online parameter of the event by the user on the Event Frame.
   *
   * @return boolean of if the event is available online
   */
  boolean observeIsOnlineFromEF();

  /**
   * Observes the inputted location parameter of the event by the user on the Event Frame.
   *
   * @return String of location details
   */
  String observeLocationFromEF();

  /**
   * Observes the inputted start day parameter of the event by the user on the Event Frame.
   *
   * @return String of start day
   */
  Day observeStartDayFromEF();

  /**
   * Observes the inputted start time parameter of the event by the user on the Event Frame.
   *
   * @return int of start time
   */
  int observeStartTimeFromEF();


  /**
   * Observes the inputted end day parameter of the event by the user on the Event Frame.
   *
   * @return String of end day
   */
  Day observeEndDayFromEF();

  /**
   * Observes the inputted end time parameter of the event by the user on the Event Frame.
   *
   * @return int of end time
   */
  int observeEndTimeFromEF();

  /**
   * Observes the inputted list of available users of the event by the user on the Event Frame.
   *
   * @return String array of usernames
   */
  List<String> observeSelectedUsersFromEF();

  /**
   * Observes the duration of the event in the Schedule Frame.
   *
   * @return a String of the host username
   */
  int observeDurationFromSF();
}
