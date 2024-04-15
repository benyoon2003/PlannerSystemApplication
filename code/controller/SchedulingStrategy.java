package controller;

import java.util.List;

import model.Event;
import model.IEvent;
import model.PlannerModel;
import model.User;

/**
 * This is the interface for all different scheduling strategies which all implement
 * the method of choosing a time based on the strategy.
 */
public interface SchedulingStrategy {

  /**
   * This method creates the event that satisfies the strategy where the possible event can fit.
   *
   * @param model     a PlannerModel
   * @param host      a User and the host of the possible event
   * @param name      a User and the host of the possible event
   * @param isOnline  a boolean to represent if the event is available online
   * @param location  a String
   * @param attendees a list of String usernames
   * @param duration  an int
   * @return an Event
   */
  IEvent chooseTime(PlannerModel model, User host, String name,
                    boolean isOnline, String location,
                    List<String> attendees, int duration);
}
