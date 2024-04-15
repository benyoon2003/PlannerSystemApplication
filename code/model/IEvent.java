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
}
