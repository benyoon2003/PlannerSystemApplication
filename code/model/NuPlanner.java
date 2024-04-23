package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the planner model. The NUPlanner is a system where someone
 * can visualize multiple users’ schedules and manipulate events on them.
 */
public final class NuPlanner implements PlannerModel {
  private List<IUser> database;

  /**
   * Constructs an NuPlanner with an empty database.
   */
  public NuPlanner() {
    this.database = new ArrayList<>();
  }

  /**
   * Constructs an NuPlanner with the given database.
   *
   * @param database a list of User
   * @implNote The passed in list of User will always be valid if the planner
   *           is instantiated following the steps described in the README
   */
  public NuPlanner(List<IUser> database) {
    this.database = database;
  }

  @Override
  public void uploadSchedule(String path) {
    IUser user = Utils.readXML(path, this.database);
    this.addUser(user);
  }

  @Override
  public void saveSchedule() {
    for (IUser user : this.database) {
      Utils.writeToFile(user, "");
    }
  }

  /**
   * This function takes in a given list of strings with the name of users to convert
   * to a list of users which makes for creating events with strings easier.
   *
   * @param users the list of string of usernames
   * @return the list of users in the same order.
   */
  private List<IUser> mapUserList(List<String> users) {
    List<IUser> userList = new ArrayList<>();
    for (String user : users) {
      userList.add(Utils.findUser(user, this.database));
    }
    return userList;
  }

  @Override
  public List<IEvent> selectSchedule(String user) {
    return Utils.findUser(user, this.database).observeSchedule();
  }

  @Override
  public Event createEvent(String user, String name, String location, boolean online,
                           Day startDay, int startTime, Day endDay,
                           int endTime, List<String> invitedUsers) {
    IUser u = Utils.findUser(user, this.database);
    if (this.database.contains(u)) {
      List<String> invitedUserCopy = new ArrayList<>(invitedUsers);
      invitedUserCopy.add(0, user);
      Event newEvent = new Event(name, location, online, startDay, startTime, endDay,
              endTime, mapUserList(invitedUserCopy));
      newEvent.sendInvite();
      return newEvent;
    } else {
      throw new IllegalArgumentException("User is not in system");
    }
  }


  @Override
  public void removeEvent(String user, IEvent e) {
    IUser u = Utils.findUser(user, this.database);
    if (this.database.contains(u)) {
      if (e.observeHost().equals(u)) {
        e.removeAll();
      } else {
        u.observeSchedule().remove(e);
      }
    }
  }


  @Override
  public void modifyEvent(IEvent e, String name, String location, boolean online,
                          Day startDay, int startTime, Day endDay,
                          int endTime, List<String> invitedUsers, String host) {
    if (startDay.equals(endDay) && startTime == endTime) {
      throw new IllegalArgumentException("Invalid Times for an Event");
    }
    e.setName(name);
    e.setLocation(location);
    e.setOnline(online);
    e.setStartDay(startDay);
    e.setStartTime(startTime);
    e.setEndDay(endDay);
    e.setEndTime(endTime);
    List<String> invitedUserCopy = new ArrayList<>(invitedUsers);
    invitedUserCopy.add(0, host);
    e.setInvitedUsers(mapUserList(invitedUserCopy));
    e.setHost(Utils.findUser(host, this.database));
  }


  @Override
  public List<IEvent> eventsAtThisTime(String user, int time) {
    IUser selected = Utils.findUser(user, this.database);
    List<IEvent> list = new ArrayList<>();
    for (IEvent e : selected.observeSchedule()) {
      if (e.observeStartTimeOfEvent() == time) {
        list.add(e);
      }
    }
    if (list.isEmpty()) {
      System.out.println("HERE");
      throw new IllegalArgumentException("No event at this time");
    } else {
      return list;
    }
  }

  @Override
  public IUser addUser(String name) {
    try {
      Utils.findUser(name, this.database);
      throw new IllegalArgumentException("Given Name already exists");
    } catch (IllegalArgumentException e) {
      IUser newUser = new User(name, List.of());
      this.database.add(newUser);
      return newUser;
    }
  }

  @Override
  public void addUser(IUser user) {
    try {
      Utils.findUser(user.toString(), this.database);
    } catch (IllegalArgumentException ignored) {
      this.database.add(new User(user.toString(), user.observeSchedule()));
    }

    try {
      for (IEvent e : user.observeSchedule()) {
        IUser userInDatabase = Utils.findUser(user.toString(), this.database);

        // Add event from new schedule if it doesn't conflict with pre-existing user's schedule
        userInDatabase.addEvent(e);
      }
    } catch (IllegalArgumentException ignored) {
      throw new IllegalArgumentException("The inputted user conflicted with the pre-existing " +
              "schedule.");
    }

  }

  @Override
  public List<IEvent> scheduleOnDay(String user, Day day) {
    IUser selected = Utils.findUser(user, this.database);
    return selected.eventsOnDay(day);
  }

  @Override
  public List<IUser> getListOfUser() {
    return this.database;
  }


  @Override
  public List<IEvent> mainSchedule() {
    List<IEvent> events = new ArrayList<>();
    for (IUser u : this.database) {
      events.addAll(u.observeSchedule());
    }
    return events;
  }

  @Override
  public IEvent findEvent(String user, int time, Day day) {
    List<IEvent> eventsOnThisDay = this.scheduleOnDay(user, day);
    List<IEvent> eventsAtThisTime = this.eventsAtThisTime(user, time);
    for (IEvent e_day : eventsOnThisDay) {
      for (IEvent e_time : eventsAtThisTime) {
        if (e_day.equals(e_time)) {
          return e_day;
        }
      }
    }
    throw new IllegalStateException("An event does not exist in the given user's time and day.");
  }
}
