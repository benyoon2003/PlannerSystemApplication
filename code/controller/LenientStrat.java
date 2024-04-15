package controller;

import java.util.List;

import model.Event;
import model.PlannerModel;
import model.User;

/**
 * THis is the strategy in which an event is scheduled at the first time that fits in work hours
 * while also ensuring that at least one invited user can attend.
 */
public class LenientStrat implements SchedulingStrategy {
  @Override
  public Event chooseTime(PlannerModel model, User host, String name,
                          boolean isOnline, String location,
                          List<String> attendees, int duration) {
    for (String attendee : attendees) {
      try {
        attendees.remove(attendee);
        if (attendees.isEmpty()) {
          break;
        }
        Event e = new WorkdayStrat().chooseTime(model, host, name,
                isOnline, location, attendees, duration);
        return e;
      } catch (IllegalArgumentException ignored) {

      }
    }
    throw new IllegalArgumentException("Given Duration cannot fit in schedule");
  }
}
