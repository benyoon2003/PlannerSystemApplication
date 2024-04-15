package controller;

import model.PlannerModel;
import model.User;
import view.EventView;

/**
 * This is the command for removing an event from the model which is called from the controller.
 */
class RemoveEventCMD implements IFeatures {
  private final PlannerModel model;
  private final EventView eventView;
  private final User host;

  /**
   * Constructs a RemoveEventCMD, which removes the corresponding Event that was populated in
   * the EventView.
   *
   * @param model     a PlannerModel
   * @param eventView an EventView
   * @param host      a User that represents the host
   */
  RemoveEventCMD(PlannerModel model, EventView eventView, User host) {
    this.model = model;
    this.eventView = eventView;
    this.host = host;
  }

  @Override
  public void execute() {
    model.removeEvent(host.toString(),
            model.findEvent(eventView.observeHostFromEF(),
                    eventView.observeStartTimeFromEF(),
                    eventView.observeStartDayFromEF()));
  }

  @Override
  public EventView observeEventView() {
    return this.eventView;
  }
}
