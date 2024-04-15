package model;

import java.util.List;

/**
 * This is an interface for models that will be passed as read only later, this
 * interface allows for only observation methods to be called and makes sure that
 * the view cannot modify the model.
 */
public interface ReadOnlyPlannerModel {

  /**
   * Select one of the users to display their schedule.
   *
   * @param user a String
   */
  public List<Event> selectSchedule(String user);

  /**
   * See events occurring at a given time for the given user.
   */
  public List<Event> eventsAtThisTime(String user, int time);


  /**
   * Finds and returns a list of events for a specified user on a given day.
   *
   * @param user the given user
   * @param day  the specifc day being requested
   * @return the list of events on that day.
   */
  public List<Event> scheduleOnDay(String user, Day day);

  /**
   * Returns a list of users that are present in the system database.
   *
   * @return returns a list of users.
   */
  public List<User> getListOfUser();

}
