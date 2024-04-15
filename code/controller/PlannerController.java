package controller;

/**
 * A PlannerController is a feature interface for the controller. It is capable of
 * getting user input from the view, manipulating the model, and reflecting changes
 * back in the view.
 */
public interface PlannerController {

  /**
   * This method is empty because its functionality is covered by the action performed method
   * inherited from the Action Listener interface. This is a feature of this specific controller but
   * other controllers might not be listeners and would utilize this method to use the controller.
   */
  public void control();


}