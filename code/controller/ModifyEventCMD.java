package controller;

import model.Event;
import model.PlannerModel;
import model.User;
import view.EventView;


/**
 * This is the action command for modifying an event in the model which is called from the
 * controller.
 */
class ModifyEventCMD implements ActionCommand {
  private final PlannerModel model;
  private final EventView eventView;
  private final User host;
  private final Event originalEvent;

  /**
   * Constructs a ModifyEventCMD, which modifies the original Event using details from the
   * EventView.
   *
   * @param model     a PlannerModel
   * @param eventView an EventView
   * @param original  an Event
   * @param host      a User that represents the host
   */
  ModifyEventCMD(PlannerModel model, EventView eventView, Event original, User host) {
    this.model = model;
    this.eventView = eventView;
    this.originalEvent = original;
    this.host = host;
  }


  @Override
  public void execute() {
    model.modifyEvent(this.originalEvent,
            eventView.observeEventNameFromEF(),
            eventView.observeLocationFromEF(),
            eventView.observeIsOnlineFromEF(),
            eventView.observeStartDayFromEF(),
            eventView.observeStartTimeFromEF(),
            eventView.observeEndDayFromEF(),
            eventView.observeEndTimeFromEF(),
            eventView.observeAvailUsersFromEF(),
            host.toString());
  }

  @Override
  public EventView observeEventView() {
    return this.eventView;
  }
}
