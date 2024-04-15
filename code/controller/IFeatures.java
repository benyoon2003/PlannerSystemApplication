package controller;


import java.util.List;

import model.Day;
import model.Event;
import model.IEvent;
import model.User;
import view.EventView;

/**
 * This is the command interface for the controller in which commands are executed.
 *
 *@implNote The ActionPerformedCommand is package protected to ensure that only the controller
 *          package is able to access it and its methods.
 */
public interface IFeatures {

  void createNewEvent(String user, String name, String location, boolean online,
                      Day startDay, int startTime, Day endDay,
                      int endTime, List<String> invitedUsers);

  void modifyEvent(IEvent originalEvent, String name, String location, boolean online,
                   Day startDay, int startTime, Day endDay,
                   int endTime, List<String> invitedUsers, String user);

  void removeEvent(String user, IEvent eventToRemove);

  void switchUser(String username);

  void uploadSchedule(String path);

  void saveSchedule(String path);

  void scheduleEvent(String host, String eventName,
                     boolean isOnline, String location,
                     List<String> attendees, int duration);

}
