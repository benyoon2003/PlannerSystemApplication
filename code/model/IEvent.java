package model;

import java.util.List;

/**
 * This is the interface representing events in this system. Each event will need to
 * be able to have observable data and setabel data. This explains the following
 * methods.
 */
public interface IEvent {


  /**
   * The following methods are observe methods that allow other object like the view
   * to extract data and visualize it.
   * @return the given field found in its name.
   */
  String observeName();

  String observeLocation();

  boolean observeOnline();

  Day observeStartDayOfEvent();

  int observeStartTimeOfEvent();

  Day observeEndDayOfEvent();

  int observeEndTimeOfEvent();

  List<IUser> observeInvitedUsers();

  IUser observeHost();

  String toString();

  /**
   * These methods are overridden because event should be the same if they share the
   * same fields like day and start time even if they are not the same object.
   * @param o the other object
   * @return if the two events are the same.
   */
  boolean equals(Object o);

  /**
   * Overridden with equals.
   * @return the hashcode
   */
  int hashCode();


  /**
   * The following are set methods that allow for data fields to be changed, this
   * exists because event data may need to be changed for modify event or for testing.
   * @param name the new name of the event.
   */
  void setName(String name);

  void setLocation(String location);

  void setOnline(boolean online);

  void setStartDay(Day startDay);

  void setStartTime(int startTime);

  void setEndTime(int endTime);

  void setEndDay(Day endDay);

  void setInvitedUsers(List<IUser> users);

  void setHost(IUser user);

  /**
   * This method is only enacted when the host removes the event. This
   * removes the event from the host's schedule and all the invitees.
   */
  void removeAll();
}
