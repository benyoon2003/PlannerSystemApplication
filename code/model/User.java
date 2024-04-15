package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


/**
 * This is the class for Users in the planner system. A User is a person in the planner system
 * with a username and a schedule.
 */
public class User {
  private String uid;
  private List<Event> schedule;

  /**
   * This is the constructor for a user which takes in a uid and a list of events
   * schedule. If the schedule has conflicts then an exception is thrown.
   *
   * @param uid      the unique identifier of the user
   * @param schedule the list of events that the user participates in
   * @throws IllegalArgumentException if the given schedule has conflicts
   */
  public User(String uid, List<Event> schedule) {
    this.uid = uid;
    this.schedule = new ArrayList<>();
    if (conflict(schedule)) {
      throw new IllegalArgumentException("Schedule has conflicts");
    } else {
      this.schedule = new ArrayList<>(schedule);
      this.sortEvents();
    }
  }

  /**
   * This method determines whether the given schedule has a conflict of events in it.
   *
   * @param schedule the given schedule
   * @return a boolean value to whether there is a conflict, true for conflict false for
   *         no conflict.
   */
  private boolean conflict(List<Event> schedule) {
    List<Event> copy = new ArrayList<Event>(schedule);
    for (Event event : schedule) {
      if (copy.isEmpty()) {
        return false;
      }
      copy.remove(event);
      for (int remainingEvents = 0; remainingEvents < copy.size(); remainingEvents++) {
        if (conflictHelper(event, copy.get(remainingEvents))) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean conflictHelper(Event one, Event two) {
    List daysOrder = List.of(Day.Sunday, Day.Monday, Day.Tuesday, Day.Wednesday,
            Day.Thursday, Day.Friday, Day.Saturday);
    int startTimeOfOne = daysOrder.indexOf(one.observeStartDayOfEvent())
            * 2400 + one.observeStartTimeOfEvent();
    int startTimeOfTwo = daysOrder.indexOf(two.observeStartDayOfEvent())
            * 2400 + two.observeStartTimeOfEvent();
    int endTimeOfOne = daysOrder.indexOf(one.observeEndDayOfEvent())
            * 2400 + one.observeEndTimeOfEvent();
    int endTimeOfTwo = daysOrder.indexOf(two.observeEndDayOfEvent())
            * 2400 + two.observeEndTimeOfEvent();
    if (startTimeOfOne > endTimeOfOne) {
      endTimeOfOne = endTimeOfOne + 10080;
    }
    if (startTimeOfTwo > endTimeOfTwo) {
      endTimeOfTwo = endTimeOfTwo + 10080;
    }
    return (startTimeOfOne <= startTimeOfTwo && endTimeOfOne > startTimeOfTwo) ||
            (startTimeOfTwo <= startTimeOfOne && endTimeOfTwo > startTimeOfOne) ||
            one.equals(two) ||
            (startTimeOfOne == startTimeOfTwo && endTimeOfOne == endTimeOfTwo);
  }

  /**
   * This method sorts the users schedule into chronological order.
   * This is done using the sort method in the list class.
   */
  private void sortEvents() {
    this.schedule.sort(new EventComparator());
  }

  /**
   * This method converts the day and time of the event into a time from Sunday at 0000.
   * This allows the comparator to compare event using one value.
   *
   * @param e the given event
   * @return the time of the event in extended dateTime
   */
  private int convertEventToStartTime(Event e) {
    List daysOrder = List.of(Day.Sunday, Day.Monday, Day.Tuesday, Day.Wednesday, Day.Thursday
            , Day.Friday, Day.Saturday);
    return daysOrder.indexOf(e.observeStartDayOfEvent()) * 2400 + e.observeStartTimeOfEvent();
  }

  /**
   * This class is a small implementation of the comparator interface to be
   * used to sort the schedule.
   */
  private class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
      return convertEventToStartTime(e1) - convertEventToStartTime(e2);
    }

  }


  /**
   * This is a method to add an event to a user while also ensuring the integrity of the
   * schedule.
   * The
   *
   * @param e the event wanting to be added
   */
  void addEvent(Event e) {
    List<Event> copy = new ArrayList<>(this.schedule);
    copy.add(e);
    if (!conflict(copy)) {
      this.schedule.add(e);
      this.sortEvents();
    } else {
      throw new IllegalArgumentException("Event conflicts with schedule");
    }
  }

  /**
   * This finds and returns a list of events on a given day.
   *
   * @param day the day given
   * @return a list of events on that day.
   */
  List<Event> eventsOnDay(Day day) {
    List<Event> events = new ArrayList<>();
    for (Event e : this.schedule) {
      if (e.observeStartDayOfEvent().equals(day) || e.observeEndDayOfEvent().equals(day)) {
        events.add(e);
      }
    }
    return events;
  }

  public List<Event> userEvents() {
    return this.schedule;
  }


  @Override
  public String toString() {
    return this.uid;
  }

  public List<Event> observeSchedule() {
    return this.schedule;
  }

  public void setSchedule(List<Event> schedule) {
    this.schedule = schedule;
  }

  @Override
  public boolean equals(Object o) {
    User u = (User) o;
    boolean sameSchedule = true;
    if (this.schedule.size() == u.schedule.size()) {
      for (int index = 0; index < this.schedule.size(); index++) {
        if (!this.schedule.get(index).equals(u.schedule.get(index))) {
          sameSchedule = false;
        }
      }
    }
    return this.uid.equals(u.uid) && sameSchedule;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uid, this.schedule);
  }

}
