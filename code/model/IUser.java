package model;

import java.util.List;

public interface IUser {

  List<IEvent> eventsOnDay(Day day);

  String toString();

  List<IEvent> observeSchedule();

  void setSchedule(List<IEvent> schedule);

  boolean equals(Object o);

  int hashCode();

  void addEvent(IEvent event);
}
