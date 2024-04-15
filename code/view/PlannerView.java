package view;

import java.awt.event.ActionListener;
import java.io.IOException;

import controller.IFeatures;
import model.Event;
import model.IEvent;
import model.User;

/**
 * This is the interface for a Planner view, which allows the user to view the schedule and
 * "modify" any available user's schedule in the model's database.
 */
public interface PlannerView {

  /**
   * Renders the given model.
   *
   * @throws IOException if render fails for some reason
   */
  void render(IEvent event, String cmd);

  /**
   * This sets the listener for the view which then connects the entire view to the controller..
   *
   * @param features is the controller being connected.
   */
  void addFeatures(IFeatures features);


  /**
   * This is a method that reloads the view after a model change which
   * reflects the changes in the model or a change in host.
   *
   * @param selectedUsername the new user being viewed or same user with new view
   * @param feature the controller needed to be reconnected to the view.
   */
  void reMakeView(String selectedUsername, IFeatures feature);

  /**
   * Displays the given error message recieved from the controller.
   * @param msg the error message
   */
  void showError(String msg);


  /**
   * This method observes which user is selected and therefore the host being viewed.
   *
   * @return the user selected.
   */
  String observeUserSelectionBox();
}
