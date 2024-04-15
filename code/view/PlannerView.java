package view;

import java.awt.event.ActionListener;
import java.io.IOException;

import controller.IFeatures;
import model.Event;
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
   * @param listener is the controller being connected.
   */
  void addFeatures(IFeatures features);


  /**
   * This is a method that reloads the view after a model change which
   * reflects the changes in the model or a change in host.
   *
   * @param host     the new host being viewed or same host with new view
   * @param listener the controller needed to be reconnected to the view.
   */
  void reMakeView(User host, ActionListener listener);

  /**
   * This method observes which user is selected and therefore the host being viewed.
   *
   * @return the user selected.
   */
  User observeUserSelectionBox();
}
