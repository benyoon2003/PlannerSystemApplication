package model;

import java.util.List;

public interface IEvent {

  String observeName();

  String observeLocation();

  boolean observeOnline();

  Day observeStartDayOfEvent();

  int observeStartTimeOfEvent();

  Day observeEndDayOfEvent();

  int observeEndTimeOfEvent();

  List<User> observeInvitedUsers();

  User observeHost();

  String toString();

  boolean equals(Object o);

  int hashCode();

  void setName(String name);

  void setLocation(String location);

  void setOnline(boolean online);

  void setStartDay(Day startDay);

  void setStartTime(int startTime);

  void setEndTime(int endTime);

  void setEndDay(Day endDay);

  void setInvitedUsers(List<User> users);

  void setHost(User user);

  void removeAll();
}
