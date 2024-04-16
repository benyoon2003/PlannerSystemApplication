package controller;

import java.util.ArrayList;
import java.util.List;

import model.IEvent;
import model.PlannerModel;
import model.User;

/**
 * THis is the strategy in which an event is scheduled at the first time that fits in work hours
 * while also ensuring that at least one invited user can attend.
 */
public class LenientStrat implements SchedulingStrategy {
  @Override
  public IEvent chooseTime(PlannerModel model, User host, String name,
                                 boolean isOnline, String location,
                                 List<String> attendees, int duration) {
    List<String> invitees = new ArrayList<>(attendees);
    if(invitees.isEmpty()){
      return new WorkHoursStrat().chooseTime(model, host, name,
              isOnline, location, attendees, duration);
    }
    while (!invitees.isEmpty()) {
      try {
        attendees.remove(invitees);
        return new WorkHoursStrat().chooseTime(model, host, name,
                isOnline, location, attendees, duration);
      } catch (IllegalArgumentException ignored) {}
    }
    throw new IllegalArgumentException("Given Duration cannot fit in schedule");
  }
}
