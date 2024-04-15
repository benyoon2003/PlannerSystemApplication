package controller;

import model.PlannerModel;
import model.User;
import view.EventView;

/**
 * This is the command from the controller creating the event in the model.
 */
class CreateEventCMD implements ActionCommand {

  private final PlannerModel model;
  private final EventView eventView;
  private final User host;

  /**
   * Constructs the CreateEventCMD, which creates the event in the model from
   * the eventView.
   *
   * @param model     a PlannerModel
   * @param eventView an EventView
   * @param host      a User to represent the host
   */
  CreateEventCMD(PlannerModel model, EventView eventView, User host) {
    this.model = model;
    this.eventView = eventView;
    this.host = host;
  }

  @Override
  public void execute() {
    model.createEvent(this.host.toString(),
            eventView.observeEventNameFromEF(),
            eventView.observeLocationFromEF(),
            eventView.observeIsOnlineFromEF(),
            eventView.observeStartDayFromEF(),
            eventView.observeStartTimeFromEF(),
            eventView.observeEndDayFromEF(),
            eventView.observeEndTimeFromEF(),
            eventView.observeAvailUsersFromEF());
    this.eventView.close();
  }

  @Override
  public EventView observeEventView() {
    return this.eventView;
  }
}
