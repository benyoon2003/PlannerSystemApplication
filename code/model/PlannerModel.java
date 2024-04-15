package model;

import java.util.List;

/**
 * The operations and observations required for a weekly planner system
 * called PlannerModel where someone can manipulate events on multiple users'
 * schedules.
 */
public interface PlannerModel extends ReadOnlyPlannerModel {

  /**
   * Upload an XML file representing a single user's schedule.
   *
   * @param path a String
   */
  public void uploadSchedule(String path);

  /**
   * Save each user’s schedule to an XML file.
   */
  public void saveSchedule();

  /**
   * Select one of the users to display their schedule.
   *
   * @param user a String
   * @return List of Event
   */
  public List<Event> selectSchedule(String user);

  /**
   * Create an event to which the given user is the host.
   *
   * @param user         host of the event
   * @param name         of the event
   * @param location     of the event
   * @param online       whether the event is online or not
   * @param startDay     of the event
   * @param startTime    of the event
   * @param endDay       of the event
   * @param endTime      of the event
   * @param invitedUsers of the event
   * @return an Event
   * @throws IllegalArgumentException if the invited list of users are not in the system
   */
  public Event createEvent(String user, String name, String location, boolean online,
                           Day startDay, int startTime, Day endDay,
                           int endTime, List<String> invitedUsers);

  /**
   * Remove an event from a user's schedule.
   *
   * @param user the given User
   * @param e    the given Event
   */
  public void removeEvent(String user, Event e);

  /**
   * Create, modify, or remove an event on a user’s schedule,
   * which may affect other user’s schedule.
   *
   * @param e            the event wanting to be modified
   * @param name         the new name of the event
   * @param location     the new location
   * @param online       still online?
   * @param startDay     the new startDay
   * @param startTime    the new startTime
   * @param endDay       the new endDay
   * @param endTime      the new endTime
   * @param invitedUsers the new list of invited users
   * @param user         the new host
   */
  public void modifyEvent(Event e, String name, String location, boolean online,
                          Day startDay, int startTime, Day endDay,
                          int endTime, List<String> invitedUsers, String user);


  /**
   * See events occurring at a given time for the given user.
   *
   * @param user a String
   * @param time an int
   * @return a List of Event
   */
  public List<Event> eventsAtThisTime(String user, int time);

  /**
   * Adds a default user to the database only if the given username does not exist.
   *
   * @param name a String
   * @return a User
   */
  public User addUser(String name);

  /**
   * Adds a User to the database of users and checks if the user already exists in the database.
   * If it does, the given user's schedule is compared to the existing user's schedule and
   * the events of the schedule are only added if none of them conflict with the pre-existing
   * schedule. If the user does not already exist in the database, it is simply added to
   * the database.
   *
   * @param user a User
   */
  public void addUser(User user);

  /**
   * Finds and returns a list of events for a specified user on a given day.
   *
   * @param user the given user
   * @param day  the specifc day being requested
   * @return the list of events on that day.
   */
  public List<Event> scheduleOnDay(String user, Day day);

  /**
   * Returns the list of User in the database.
   *
   * @return a list of User
   */
  public List<User> getListOfUser();

  /**
   * Allows for the client to query the main event schedule.
   *
   * @return the main schedule
   */
  public List<Event> mainSchedule();

  /**
   * Finds the event on the given user's day and time.
   *
   * @param user a username
   * @param time an int
   * @param day  a Day
   * @return an Event
   */
  public Event findEvent(String user, int time, Day day);
}
