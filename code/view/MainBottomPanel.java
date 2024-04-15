package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import controller.IFeatures;
import model.ReadOnlyPlannerModel;
import model.User;
import model.Utils;


/**
 * This is the panel at the bottom of the main system frame which holds the select box to
 * select user, the create event button, and the schedule event button. This panel allows for
 * more user control in this frame. In this file there are the features of this frame that connect
 * to the relevant EventView which would then be connected to the controller. This
 * panel mutates the entire week view to show the selected users schedule.
 * We thought this should be a feature of the view and not controller as the user is merely
 * shifting their view throughout the model and not modifying any data. Therefore,
 * that functionality is in this class.
 *
 * @implNote This should be package protected because this panel should not leak
 *           information outside the view package.
 */
class MainBottomPanel extends JPanel {

  private final ReadOnlyPlannerModel model;

  private final String selectedUsername;

  private JComboBox<String> selectedUser;

  private JButton createEvent;
  private JButton scheduleEvent;

  private IFeatures features;


  /**
   * This constructs the bottom panel of the main scheduler frame.
   * which takes in the following as parameters.
   *
   * @param model a ReadOnlyPlannerModel
   * @param host  a User to represent the host
   */
  MainBottomPanel(ReadOnlyPlannerModel model, String selectedUsername) {
    this.model = Objects.requireNonNull(model);
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(800, -600));
    this.selectedUsername = selectedUsername;

    makeSelectUserBox();
    makeEventButtons();

  }


  /**
   * This creates the JComboBox in which the user can select a user to view. Changing
   * the user changes the main schedule frame's view to reflect the schedule of
   * the selected user.
   */
  private void makeSelectUserBox() {
    this.selectedUser = new JComboBox<>(Utils.convertToStringArray(model.getListOfUser()));
    this.selectedUser.setSelectedItem(this.selectedUser);
    this.add(selectedUser);
    this.selectedUser.setActionCommand("Select User Box");
  }

  /**
   * This method converts a given list of users to an array of users
   * to use in the JComboBox and select users. This is used in the
   * mouse clicked method which opens the event dialogue box with the
   * list of users in the event.
   *
   * @param users the list of users in the event
   * @return a mirroring array of users
   */
  private User[] convertToUserArray(List<User> users) {
    User[] usernames = new User[users.size()];
    for (int index = 0; index < users.size(); index++) {
      usernames[index] = users.get(index);
    }
    return usernames;
  }

  /**
   * This method creates the "Create Event" Button and the "Schedule Event" button
   * that should be at the bottom of this panel as specified in the assignment. These
   * buttons once clicked call and open the Event Frame view where a user can modify or
   * create an event.
   */
  private void makeEventButtons() {
    createEvent = new JButton("Create Event");
    createEvent.setActionCommand("Create Event Main Button");
    scheduleEvent = new JButton("Schedule Event");
    scheduleEvent.setActionCommand("Schedule Event Button");
    createEvent.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<User> userList = new ArrayList<>(model.getListOfUser());
        userList.remove(selectedUser);
        EventView newEvent = new EventFrameView(Utils.convertToStringArray(model.getListOfUser()),
                selectedUser.toString());
        newEvent.addFeatures(features);
        newEvent.display();
      }
    });
    scheduleEvent.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EventView scheduleFrame =
                new ScheduleFrame(Utils.convertToStringArray(model.getListOfUser()));
        scheduleFrame.addFeatures(features);
        scheduleFrame.display();
      }
    });
    this.add(createEvent);
    this.add(scheduleEvent);
  }

  /**
   * Observes the selected user from the JComboBox.
   *
   * @return a User
   */
  String observeUserSelectionBox() {
    return (String) this.selectedUser.getSelectedItem();
  }

  /**
   * Sets the listeners for the JButtons.
   *
   * @param features the feature performing commands.
   */
  void addFeature(IFeatures features) {
    this.features = features;
    selectedUser.addActionListener(evt ->
            features.switchUser(observeUserSelectionBox().toString()));
  }

}
