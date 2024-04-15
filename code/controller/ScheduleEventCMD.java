package controller;


import model.PlannerModel;
import model.User;
import view.EventView;


/**
 * This the command for scheduling an event using the given strategy which then creates the event
 * in the model. This class takes in the model, the event view with the given params, the host,
 * and the strategy implemented.
 */
class ScheduleEventCMD implements IFeatures {

  private final PlannerModel model;
  private final EventView eventView;
  private final User host;
  private final SchedulingStrategy strat;


  /**
   * Constructs a ScheduleEventCMD, which schedules an event in the system using
   * the given strategy and EventView details.
   *
   * @param model     a PlannerModel
   * @param eventView an EventView
   * @param host      a User that represents the host
   * @param strat     a SchedulingStrategy
   */
  ScheduleEventCMD(PlannerModel model, EventView eventView, User host,
                   SchedulingStrategy strat) {
    this.model = model;
    this.eventView = eventView;
    this.host = host;
    this.strat = strat;
  }

  @Override
  public void execute() {
    int duration = this.eventView.observeDurationFromSF();
    this.strat.chooseTime(this.model, this.host, eventView.observeEventNameFromEF(),
            eventView.observeIsOnlineFromEF(), eventView.observeLocationFromEF(),
            eventView.observeAvailUsersFromEF(), duration);
  }

  @Override
  public EventView observeEventView() {
    return this.eventView;
  }
}
