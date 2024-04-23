package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import controller.IFeatures;
import model.IUser;
import model.ReadOnlyPlannerModel;


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

  private JComboBox<String> selectedUserBox;

  private JButton createEvent;
  private JButton scheduleEvent;
  private JButton toggleColor;

  private boolean isColorToggled;

  private IFeatures features;


  /**
   * This constructs the bottom panel of the main scheduler frame.
   * which takes in the following as parameters.
   *
   * @param model a ReadOnlyPlannerModel
   * @param selectedUsername is the user that is currently selected
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
    this.selectedUserBox = new JComboBox<>(convertToStringArray(model.getListOfUser()));
    this.selectedUserBox.setSelectedItem(this.selectedUsername);
    this.add(selectedUserBox);
    this.selectedUserBox.setActionCommand("Select User Box");
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
  private static String[] convertToStringArray(List<IUser> users) {
    String[] usernames = new String[users.size()];
    for (int index = 0; index < users.size(); index++) {
      usernames[index] = users.get(index).toString();
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
    toggleColor = new JButton("Toggle host color");
    createEvent.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<IUser> userList = new ArrayList<>(model.getListOfUser());
        userList.remove(findUser(selectedUsername, model.getListOfUser()));
        EventView newEvent = new EventFrameView(convertToStringArray(userList),
                selectedUsername);
        newEvent.addFeatures(features);
        newEvent.display();
      }

      /**
       * Gets the User with the given usernamen in the given database.
       *
       * @param userName a String
       * @param database a List of User
       * @return a User
       */
      private static IUser findUser(String userName, List<IUser> database) {
        for (IUser user : database) {
          if (user.toString().equals(userName)) {
            return user;
          }
        }
        throw new IllegalArgumentException("User not found");
      }
    });
    scheduleEvent.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<IUser> notHost = new ArrayList<>(model.getListOfUser());
        notHost.remove(
                this.findUser(selectedUsername, model.getListOfUser()));
        EventView scheduleFrame =
                new ScheduleFrame(convertToStringArray(notHost),
                        selectedUsername);
        scheduleFrame.addFeatures(features);
        scheduleFrame.display();
      }

      /**
       * Gets the User with the given usernamen in the given database.
       *
       * @param userName a String
       * @param database a List of User
       * @return a User
       */
      private static IUser findUser(String userName, List<IUser> database) {
        for (IUser user : database) {
          if (user.toString().equals(userName)) {
            return user;
          }
        }
        throw new IllegalArgumentException("User not found");
      }

    });

    toggleColor.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        isColorToggled = !isColorToggled;
      }
    });

    this.add(createEvent);
    this.add(scheduleEvent);
    this.add(toggleColor);
  }

  /**
   * Observes the selected user from the JComboBox.
   *
   * @return a User
   */
  String observeUserSelectionBox() {
    return (String) this.selectedUserBox.getSelectedItem();
  }

  boolean toggleColor() {
    return isColorToggled;
  }

  /**
   * Sets the listeners for the JButtons.
   *
   * @param features the feature performing commands.
   */
  void addFeature(IFeatures features) {
    this.features = features;
    selectedUserBox.addActionListener(evt ->
            features.switchUser(observeUserSelectionBox()));
    toggleColor.addActionListener(evt ->
            features.toggleColor());
  }

}
