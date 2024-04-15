package model;

import java.util.List;
import java.util.Objects;

/**
 * This is the event class which represents an event in the system. An event consists of
 * the name of the event, the location, whether or not it is available online, the
 * start day, start time, end day, end time, a list of invited users (including the host).
 *
 * <p>This class should be public as clients would assume and also know that Events are objects
 * in the system. The context through which we discuss events is used as a noun and is
 * mirrored here. Only users and Events should be public.</p>
 *
 * @implNote The event class's methods arte package protected meaning only other classes within the
 *           model are able to access them.
 */
public final class Event {
  private String name;
  private String location;
  private boolean online;
  private Day startDay;
  private int startTime;
  private Day endDay;
  private int endTime;
  private List<User> invitedUsers;

  private User host;

  /**
   * Constructor for an event.
   *
   * @param name         of the event and cannot be null
   * @param location     of the event and cannot be null
   * @param online       boolean for whether the event is online or not
   * @param startDay     start day event
   * @param startTime    start time of the event THIS IS OUR INVARIANT-- start times cannot be
   *                     negative
   * @param endDay       end day of the event
   * @param endTime      end time of the event THIS IS OUR INVARIANT-- end times cannot be negative
   * @param invitedUsers users that are a part of the event
   */
  Event(String name, String location, boolean online,
        Day startDay, int startTime, Day endDay,
        int endTime, List<User> invitedUsers) {
    if (startDay.equals(endDay) && startTime == endTime) {
      throw new IllegalArgumentException("Invalid Times for an Event");
    }
    this.name = Objects.requireNonNull(name);
    this.location = Objects.requireNonNull(location);
    this.online = online;
    this.startDay = Objects.requireNonNull(startDay);
    if (startTime >= 0 && startTime < 2400) {
      this.startTime = startTime;
    } else {
      throw new IllegalArgumentException("Invalid Start Time");
    }
    this.endDay = Objects.requireNonNull(endDay);
    if (endTime >= 0 && endTime < 2400) {
      this.endTime = endTime;
    } else {
      throw new IllegalArgumentException("Invalid End Time");
    }
    this.invitedUsers = Objects.requireNonNull(invitedUsers);
    if (!this.invitedUsers.isEmpty()) {
      this.host = this.invitedUsers.get(0);
    }
  }


  /**
   * Getter for the name.
   *
   * @return the name of the event
   */
  public String observeName() {
    return this.name;
  }

  /**
   * Getter for the location.
   *
   * @return the location of the event.
   */
  public String observeLocation() {
    return this.location;
  }

  /**
   * Getter for the field online.
   *
   * @return whether the event is online
   */
  public boolean observeOnline() {
    return this.online;
  }

  /**
   * Observation for the start day of the event.
   *
   * @return the starting day of the event
   */
  public Day observeStartDayOfEvent() {
    return this.startDay;
  }

  /**
   * Observation for the start time of the event.
   *
   * @return the start time of the event
   */
  public int observeStartTimeOfEvent() {
    return this.startTime;
  }

  /**
   * Observation for the end day of the event.
   *
   * @return the end day of the event.
   */
  public Day observeEndDayOfEvent() {
    return this.endDay;
  }

  /**
   * Getter for end time.
   *
   * @return the end time of the event.
   */
  public int observeEndTimeOfEvent() {
    return this.endTime;
  }

  /**
   * Getter for the invited users.
   *
   * @return the invited users of the event
   */
  public List<User> observeInvitedUsers() {
    return this.invitedUsers;
  }

  /**
   * Sets the invited users of this event to the given list of User.
   *
   * @param attendees a list of User
   */
  void setInvitedUsers(List<User> attendees) {
    if (attendees == null) {
      throw new IllegalArgumentException("Given List cannot be null");
    }
    this.updateUsers(this.invitedUsers, attendees);
    this.invitedUsers = attendees;
  }

  /**
   * Compares the old list of invitees with the updated list of invitees and
   * removes this event from the schedule of every user that is no longer
   * in the updated list of invitees as well as adds this event to every user in the
   * updated list of invitees.
   *
   * @param old    list of User
   * @param update list of User
   */
  private void updateUsers(List<User> old, List<User> update) {
    for (User u : old) {
      if (!update.contains(u)) {
        u.observeSchedule().remove(this);
      }
    }
    for (User u : update) {
      if (!u.observeSchedule().contains(this)) {
        u.addEvent(this);
      }
    }
  }

  /**
   * Adds this event to the schedule of every user in the list of invitees.
   */
  void sendInvite() {
    for (User attendee : this.invitedUsers) {
      if (attendee.equals(this.host)) {
        attendee.addEvent(this);
      } else {
        try {
          attendee.addEvent(this);
        } catch (IllegalArgumentException ignored) {
        }
      }
    }
  }

  /**
   * Removes this event from the schedule of all users in the list of invitees.
   */
  void removeAll() {
    for (User attendee : this.invitedUsers) {
      attendee.observeSchedule().remove(this);
    }
  }

  /**
   * Getter for host of the event.
   *
   * @return the host of the event.
   */
  public User observeHost() {
    return this.host;
  }

  /**
   * Sets the name of the event.
   *
   * @param name a String
   */
  void setName(String name) {
    this.name = Objects.requireNonNull(name);
  }

  /**
   * Sets the location of the event.
   *
   * @param location a String
   */
  void setLocation(String location) {
    this.location = Objects.requireNonNull(location);
  }

  /**
   * Sets whether or not this event is available online.
   *
   * @param online a boolean
   */
  void setOnline(boolean online) {
    this.online = online;
  }

  /**
   * Sets the start day of this event.
   *
   * @param startDay a Day
   */
  void setStartDay(Day startDay) {
    this.startDay = Objects.requireNonNull(startDay);
  }

  /**
   * Sets the start time of this event.
   *
   * @param time an int
   */
  void setStartTime(int time) {
    if (time >= 0 && time < 2400) {
      this.startTime = time;
    } else {
      throw new IllegalArgumentException("Invalid Start Time");
    }
  }

  /**
   * Sets the end day of this event.
   *
   * @param endDay a Day
   */
  void setEndDay(Day endDay) {
    this.endDay = Objects.requireNonNull(endDay);
  }

  /**
   * Sets the end time of this event.
   *
   * @param time an int
   */
  void setEndTime(int time) {
    if (time >= 0 && time < 2400) {
      this.endTime = time;
    } else {
      throw new IllegalArgumentException("Invalid End Time");
    }
  }

  /**
   * Sets the host of this event.
   *
   * @param newHost a User
   */
  void setHost(User newHost) {
    this.host = newHost;
  }

  /**
   * Converts the list of invitees to a string.
   *
   * @return a String
   */
  private String convertListOfInvitees() {
    String invitees = "";
    for (User u : this.invitedUsers) {
      invitees += u.toString() + "\n       ";
    }
    return invitees;
  }

  /**
   * Creates a string representation of an Event.
   */
  @Override
  public String toString() {
    String output = "";
    output += "       name: " + this.name + "\n";
    output += "       time: " + this.startDay.toString() + String.format(": %d -> ",
            this.startTime);
    output += this.endDay.toString() + String.format(": %d\n", this.endTime);
    output += "       location: " + this.location + "\n";
    output += "       online: " + this.online + "\n";
    output += "       invitees: " + convertListOfInvitees() + "\n";
    return output;
  }

  @Override
  public boolean equals(Object o) {
    Event e = (Event) o;
    return this.name.equals(e.name)
            && this.online == e.online
            && this.invitedUsers.equals(e.invitedUsers)
            && this.startDay.equals(e.startDay)
            && this.startTime == e.startTime
            && this.endDay.equals(e.endDay)
            && this.endTime == e.endTime
            && this.location.equals(e.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.online, this.invitedUsers, this.startDay,
            this.startTime, this.endDay, this.endTime, this.location);
  }
}
