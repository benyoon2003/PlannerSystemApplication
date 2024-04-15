package controller;

import java.util.List;
import java.util.Objects;

import model.Day;
import model.Event;
import model.IEvent;
import model.PlannerModel;
import model.User;
import model.Utils;
import view.EventView;
import view.PlannerView;

/**
 * This is the controller for an NU planner system. This class implements both the
 * overhead controller interface while also implementing an action listener to
 * perform actions after picking up an action in the view. This controller takes
 * in a model, a strategy, and a view.
 */
public class NUPlannerController implements IFeatures {
  private final PlannerModel model;
  private final PlannerView view;
  private EventView eventView;
  private User host;
  private final SchedulingStrategy strat;
  private Event originalEvent;

  /**
   * Constructs a NUPlannerController, which takes in inputs from the PlannerView, modifies
   * the PlannerModel, and outputs changes to the PlannerView.
   *
   * @param model a PlannerModel
   * @param strat a SchedulingStrategy
   * @param view  a PlannerView
   */
  public NUPlannerController(PlannerModel model, SchedulingStrategy strat, PlannerView view) {
    this.model = Objects.requireNonNull(model);
    this.strat = Objects.requireNonNull(strat);
    this.view = Objects.requireNonNull(view);
    // Defaults host to first user in database
    this.host = model.getListOfUser().get(0);
    this.view.addFeatures(this);
  }

  /**
   * Constructs a NUPlannerController that takes in inputs from the PlannerView,
   * modifies the PlannerModel, and outputs changes to the plannerView.
   * This constructor also takes in EventView and host for testing purposes.
   *
   * @param model     a PlannerModel
   * @param strat     a SchedulingStrategy
   * @param view      a PlannerView
   * @param eventView an EventView
   * @param host      a User
   */
  public NUPlannerController(PlannerModel model, SchedulingStrategy strat,
                             PlannerView view, EventView eventView, User host) {
    this.model = Objects.requireNonNull(model);
    this.strat = Objects.requireNonNull(strat);
    this.view = Objects.requireNonNull(view);
    this.eventView = Objects.requireNonNull(eventView);
    this.host = Objects.requireNonNull(host);
    this.view.addFeatures(this);
  }


  /**
   * Observes the eventView member variable.
   *
   * @return EventView
   * @implNote This method is package protected and does not exist in the interface
   *           because it is specific to this implementation of PlannerController and
   *           other controllers may not utilize EventView.
   */
  EventView observeEventView() {
    return this.eventView;
  }

  /**
   * Sets the eventView member variable.
   *
   * @param eventView an EventView
   * @implNote This method is package protected and does not exist in the interface
   *           because it is specific to this implementation of PlannerController and
   *           other controllers may not utilize EventView.
   */
  void setEventView(EventView eventView) {
    this.eventView = eventView;
  }

  public void control() {
    // Javastyle is forcing us to use this member variable in other methods so we threw it in here
    // sorry and thank you!
    this.originalEvent = null;
  }

  @Override
  public void createNewEvent(String user, String name,
                             String location, boolean online, Day startDay,
                             int startTime, Day endDay, int endTime, List<String> invitedUsers) {
    try {
      model.createEvent(user, name, location, online, startDay, startTime, endDay, endTime,
              invitedUsers);
    } catch (IllegalArgumentException er){
      view.showError(er.getMessage());
    }

  }

  @Override
  public void modifyEvent(IEvent originalEvent, String name, String location,
                          boolean online, Day startDay, int startTime, Day endDay,
                          int endTime, List<String> invitedUsers, String user) {
    try {
      model.modifyEvent(originalEvent, name, location, online, startDay, startTime, endDay, endTime,
              invitedUsers, user);
    } catch (IllegalArgumentException er){
      view.showError(er.getMessage());
    }
  }

  @Override
  public void removeEvent(String user, IEvent eventToRemove) {
    try {
      model.removeEvent(user, eventToRemove);
    } catch (IllegalArgumentException er){
      view.showError(er.getMessage());
    }
  }

  @Override
  public void switchUser(String username) {
    this.host = Utils.findUser(username, this.model.getListOfUser());
    view.reMakeView(Utils.findUser(username, this.model.getListOfUser()), this);
  }

  @Override
  public void uploadSchedule(String path) {
    try{
      model.addUser(Utils.readXML(path, model.getListOfUser()));
      view.reMakeView(host, this);
    } catch (IllegalArgumentException | NullPointerException er) {
      view.showError(er.getMessage());
    }
  }

  @Override
  public void saveSchedule(String path) {
    for (User u : model.getListOfUser()) {
      Utils.writeToFile(u, path);
    }
  }

  @Override
  public void scheduleEvent(String host, String eventName, boolean isOnline,
                            String location, List<String> attendees, int duration) {
    this.strat.chooseTime(this.model, Utils.findUser(host, this.model.getListOfUser()),
            eventName, isOnline, location, attendees, duration);
  }
}
