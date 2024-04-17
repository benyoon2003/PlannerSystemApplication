package controller;

import java.util.List;

import model.Day;
import model.IEvent;
import model.IUser;
import model.PlannerModel;
import model.User;


/**
 * This is the strategy for scheduling an event during work hours. This is why the days order does
 * not include weekends and also has limited times.
 */
public class WorkHoursStrat implements SchedulingStrategy {
  @Override
  public IEvent chooseTime(PlannerModel model, IUser host, String name,
                           boolean isOnline, String location,
                           List<String> attendees, int duration) {
    java.util.List<Day> daysOrder = java.util.List.of(
            Day.Monday, Day.Tuesday, Day.Wednesday, Day.Thursday,
            Day.Friday);
    int daysAdded = duration / 2400;
    for (int day = 0; day < (daysOrder.size() - daysAdded); day++) {
      duration = duration % 2400;
      for (int time = 900; time < (1700 - duration); time += duration) {
        try {
          return model.createEvent(host.toString(), name, location,
                  isOnline, daysOrder.get(day), time, daysOrder.get(day + daysAdded),
                  time + duration, attendees);
        } catch (IllegalArgumentException ignored) {
        }
      }
    }
    throw new IllegalArgumentException("Given Duration cannot fit in schedule");
  }
}
