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

  /**
   * Higher Level call for the user requesting to create and event from the view.
   * @param user from which the event is being created / the host
   * @param name the name of the event
   * @param location the location of the event
   * @param online whether the event is online
   * @param startDay the starting day of the event
   * @param startTime the starting time of the event
   * @param endDay the end day of the event
   * @param endTime the endtime of the event
   * @param invitedUsers the invited users
   */
  void createNewEvent(String user, String name, String location, boolean online,
                      Day startDay, int startTime, Day endDay,
                      int endTime, List<String> invitedUsers);

  /**
   * Higher Level call for the user requesting to modify an event from the view.
   * @param originalEvent the event being modified
   * @param name the new name
   * @param location the new location
   * @param online still online?
   * @param startDay the new starting day
   * @param startTime the new start
   * @param endDay the new endDay
   * @param endTime the new end time
   * @param invitedUsers the new invited users
   * @param user the host of the modified event
   */
  void modifyEvent(IEvent originalEvent, String name, String location, boolean online,
                   Day startDay, int startTime, Day endDay,
                   int endTime, List<String> invitedUsers, String user);

  /**
   * The higher level call for the user requesting to remove an event
   * @param user the user requesting the removal
   * @param eventToRemove the event being requested to remove
   */
  void removeEvent(String user, IEvent eventToRemove);

  /**
   * The higher level action of switching the user in the view and requesting a new view
   * @param username the user being selected
   */
  void switchUser(String username);

  /***
   * The higher level call to add a schedule to the system
   * @param path the path of the file with the new schedule
   */
  void uploadSchedule(String path);

  /**
   * The higher level call to save a schedule to a given path
   * @param path of where the file will be saved
   */
  void saveSchedule(String path);

  /**
   * THe higher level call to schedule an event with the given strategy.
   * @param host of the event
   * @param eventName the event name
   * @param isOnline whether the event is online
   * @param location the location of the event
   * @param attendees the attendees of the event
   * @param duration the duration of the event.
   */
  void scheduleEvent(String host, String eventName,
                     boolean isOnline, String location,
                     List<String> attendees, int duration);

  /**
   * This is a higher level call to change the view so that the host can see
   * the event they are hosts of.
   */
  void toggleHostView();

}
