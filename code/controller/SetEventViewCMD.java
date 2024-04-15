package controller;


import model.Event;
import model.PlannerModel;
import model.Utils;
import view.EventFrameView;
import view.EventView;

/**
 * This is the command for creating an event frame view off of clicking an event in the swing view
 * or in the more general sense selecting a preexisting event.
 */
class SetEventViewCMD implements IFeatures {

  private final PlannerModel model;
  private EventView eventView;

  private final Event originalEvent;

  /**
   * Constructs a SetEventViewCMD that populates an EventView off mouseclick from the main
   * frame.
   *
   * @param model     a PlannerModel
   * @param eventView an EventView
   * @param original  an Event
   */
  SetEventViewCMD(PlannerModel model, EventView eventView, Event original) {
    this.model = model;
    this.eventView = eventView;
    this.originalEvent = original;
  }

  @Override
  public void execute() {
    System.out.println("Set Event View");
    System.out.println(this.originalEvent.toString());
    EventView event = new EventFrameView(this.originalEvent.observeName(),
            this.originalEvent.observeOnline(),
            this.originalEvent.observeLocation(), this.originalEvent.observeStartDayOfEvent(),
            Integer.toString(this.originalEvent.observeStartTimeOfEvent()),
            this.originalEvent.observeEndDayOfEvent(),
            Integer.toString(this.originalEvent.observeEndTimeOfEvent()),
            Utils.convertToStringArray(this.model.getListOfUser()),
            this.originalEvent.observeHost().toString(),
            Utils.convertToStringArray(this.originalEvent.observeInvitedUsers()));
    this.eventView = event;
  }

  public EventView observeEventView() {
    return this.eventView;
  }
}
