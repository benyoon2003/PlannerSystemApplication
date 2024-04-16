package view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.ListSelectionModel;

import controller.IFeatures;
import model.Day;
import model.IEvent;
import model.Utils;

/**
 * The EventFrameView is a custom JFrame that implements EventView. It contains components
 * that allows the user to input details for an event and create it
 * as well as read details that already exist within the event. The details within an event
 * include the following:
 * the name of the event, whether or not the event is available online, the location of the
 * event, the starting day, starting time, ending day, ending time, and a list of users invited
 * to the event.
 *
 * @implNote The eventPanel member variable is final because components are placed on
 *           top of it and the overarching panel itself does not change.
 *           The components that are laid on top of the eventPanel are the ones being modified,
 *           thus they are not final. The start time and end time are Strings to make it easier
 *           to modify components.
 */
public class EventFrameView extends JFrame implements EventView {
  private final String selectedUsername;
  private final JPanel eventPanel;
  private JTextArea name;
  private JComboBox<String> isOnline;
  private JTextArea location;
  private JComboBox<Day> startingDay;
  private JTextArea startingTime;
  private JComboBox<Day> endingDay;
  private JTextArea endingTime;
  private JList<String> availUser;

  private JButton createButton;

  private JButton modifyButton;

  private JButton removeButton;
  private IEvent originalEvent;

  /**
   * Constructs a default EventFrameView that contains default components of an event and
   * sets the host according to the given username.
   *
   * @param availUsers the list of available users in the event.
   * @param selectedUsername       a username as a String
   */
  public EventFrameView(String[] availUsers, String selectedUsername) {
    this.selectedUsername = selectedUsername;
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.eventPanel = new JPanel();
    this.eventPanel.setLayout(new BoxLayout(this.eventPanel, BoxLayout.Y_AXIS));
    makeNamePanel("");
    makeLocationPanel(false, "");
    makeStartDayPanel(Day.Sunday);
    makeStartTimePanel("");
    makeEndDayPanel(Day.Sunday);
    makeEndTimePanel("");
    makeAvailUserPanel(availUsers, new String[]{});
    makeButtonPanel();

    this.add(this.eventPanel);
    this.pack();

  }

  /**
   * Constructs a EventFrameView using the pre-existing details of the event allowing
   * for the user to choose to modify parts of an existing event.
   *
   * @param availUsers   String array
   */
  public EventFrameView(IEvent originalEvent, String[] availUsers, String selectedUsername) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.originalEvent = originalEvent;
    this.selectedUsername = selectedUsername;
    this.eventPanel = new JPanel();
    this.eventPanel.setLayout(new BoxLayout(this.eventPanel, BoxLayout.Y_AXIS));

    makeNamePanel(this.originalEvent.observeName());
    makeLocationPanel(this.originalEvent.observeOnline(), this.originalEvent.observeLocation());
    makeStartDayPanel(this.originalEvent.observeStartDayOfEvent());
    makeStartTimePanel(Integer.toString(this.originalEvent.observeStartTimeOfEvent()));
    makeEndDayPanel(this.originalEvent.observeEndDayOfEvent());
    makeEndTimePanel(Integer.toString(this.originalEvent.observeEndTimeOfEvent()));
    makeAvailUserPanel(availUsers, Utils.convertToStringArray(this.originalEvent.observeInvitedUsers()));
    makeButtonPanel();

    this.add(this.eventPanel);
    this.pack();

  }

  /**
   * Creates the name panel that allows modification of the event name.
   * Rests on top of the main event panel.
   *
   * @param eventName a String
   */
  private void makeNamePanel(String eventName) {
    JPanel nameLabelPanel = new JPanel();
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new BorderLayout());
    JLabel nameLabel = new JLabel("Event name:");
    this.name = new JTextArea(eventName, 1, 10);
    this.name.setLineWrap(true);
    this.name.setWrapStyleWord(true);
    namePanel.add(this.name);
    nameLabelPanel.add(nameLabel);
    nameLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.eventPanel.add(nameLabelPanel);
    this.eventPanel.add(namePanel);
  }

  /**
   * Creates the location panel which contains whether or not the event is available online
   * as well as the location of the event. Rests on top of the main event panel.
   *
   * @param isOnline boolean
   * @param location String
   */
  private void makeLocationPanel(boolean isOnline, String location) {
    JPanel locationLabelPanel = new JPanel();
    JPanel locationPanel = new JPanel();
    JLabel locationLabel = new JLabel("Location:");
    String[] isOnlineList = {"Is online", "Not online"};
    this.isOnline = new JComboBox<>(isOnlineList);
    if (isOnline) {
      this.isOnline.setSelectedIndex(0);
    } else {
      this.isOnline.setSelectedIndex(1);
    }
    this.location = new JTextArea(location, 1, 10);
    this.location.setLineWrap(true);
    this.location.setWrapStyleWord(true);
    locationPanel.add(this.isOnline);
    locationPanel.add(this.location);
    locationLabelPanel.add(locationLabel);
    locationLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.eventPanel.add(locationLabelPanel);
    this.eventPanel.add(locationPanel);
  }

  /**
   * Creates the start day panel which allows modification of the start day of the event.
   * Rests on top of the main event panel.
   *
   * @param day a Day
   */
  private void makeStartDayPanel(Day day) {
    JPanel startingDayPanel = new JPanel();
    JLabel startingDayLabel = new JLabel("Starting Day:");
    Day[] days = {Day.Sunday, Day.Monday, Day.Tuesday, Day.Wednesday,
      Day.Thursday, Day.Friday, Day.Saturday};
    this.startingDay = new JComboBox<>(days);
    this.startingDay.setSelectedItem(day);
    startingDayPanel.add(startingDayLabel);
    startingDayPanel.add(this.startingDay);
    this.eventPanel.add(startingDayPanel);
  }

  /**
   * Creates the start time panel which allows for modification of the start time of the event.
   * Rests on top of the main event panel.
   *
   * @param startTime a String
   */
  private void makeStartTimePanel(String startTime) {
    JPanel startingTimePanel = new JPanel();
    JLabel startingTimeLabel = new JLabel("Starting time:");
    this.startingTime = new JTextArea(startTime, 1, 10);
    this.startingTime.setLineWrap(true);
    this.startingTime.setWrapStyleWord(true);
    startingTimePanel.add(startingTimeLabel);
    startingTimePanel.add(this.startingTime);
    this.eventPanel.add(startingTimePanel);
  }

  /**
   * Creates the end day panel which allows modification of the end day of the event.
   * Rests on top of the main event panel.
   *
   * @param day a Day
   */
  private void makeEndDayPanel(Day day) {
    JPanel endingDayPanel = new JPanel();
    JLabel endingDayLabel = new JLabel("Ending Day:");
    Day[] days = {Day.Sunday, Day.Monday, Day.Tuesday, Day.Wednesday,
        Day.Thursday, Day.Friday, Day.Saturday};
    this.endingDay = new JComboBox<>(days);
    this.endingDay.setSelectedItem(day);
    endingDayPanel.add(endingDayLabel);
    endingDayPanel.add(this.endingDay);
    this.eventPanel.add(endingDayPanel);
  }

  /**
   * Creates the end time panel which allows for modification of the end time of the event.
   * Rests on top of the main event panel.
   *
   * @param endTime a String
   */
  private void makeEndTimePanel(String endTime) {
    JPanel endingTimePanel = new JPanel();
    JLabel endingTimeLabel = new JLabel("Ending time:");
    this.endingTime = new JTextArea(endTime, 1, 10);
    this.endingTime.setLineWrap(true);
    this.endingTime.setWrapStyleWord(true);
    endingTimePanel.add(endingTimeLabel);
    endingTimePanel.add(this.endingTime);
    this.eventPanel.add(endingTimePanel);
  }

  /**
   * Creates the invited user panel which allows for modification of the list of invited users.
   * Rests on top of the main event panel.
   *
   * @param availUsers   a String array of all available users
   * @param prevSelected a String array of previously selected users
   */
  private void makeAvailUserPanel(String[] availUsers, String[] prevSelected) {
    JPanel availUserLabelPanel = new JPanel();
    availUserLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel availUserLabel = new JLabel("Available users");
    JPanel availUserPanel = new JPanel(); // Panel for available users
    availUserPanel.setLayout(new BoxLayout(availUserPanel, BoxLayout.Y_AXIS));
    int indexOfHost = -1;
    for (int indexAvail = 0; indexAvail < availUsers.length; indexAvail++) {
      if (this.originalEvent.observeHost().toString().equals(availUsers[indexAvail])) {
        indexOfHost = indexAvail;
      }
    }
    availUsers[indexOfHost] = availUsers[0];
    availUsers[0] = this.originalEvent.observeHost().toString();
    this.availUser = new JList<>(availUsers);
    this.availUser.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    int[] selectionIndices = new int[prevSelected.length];
    for (int index = 0; index < prevSelected.length; index++) {
      for (int users = 0; users < availUsers.length; users++) {
        if (prevSelected[index].equals(availUsers[users])) {
          selectionIndices[index] = users;
        }
      }
    }
    this.availUser.setSelectedIndices(selectionIndices);
    availUserPanel.add(this.availUser);
    availUserLabelPanel.add(availUserLabel);
    this.eventPanel.add(availUserLabelPanel);
    this.eventPanel.add(availUserPanel);
  }

  /**
   * Creates the create event, modify event, and remove event buttons, which rest on top
   * of the main event panel.
   */
  private void makeButtonPanel() {
    JPanel buttonPanel = new JPanel();
    createButton = new JButton("Create event");
    createButton.setActionCommand("Create Event Button");
    modifyButton = new JButton("Modify event");
    modifyButton.setActionCommand("Modify Event Button");
    removeButton = new JButton("Remove event");
    removeButton.setActionCommand("Remove Event Button");
    buttonPanel.add(createButton);
    buttonPanel.add(modifyButton);
    buttonPanel.add(removeButton);
    this.eventPanel.add(buttonPanel);
  }

  /**
   * Checks if any of the required text fields for an event are empty.
   *
   * @return a boolean
   */
  public boolean validInput() {
    return !name.getText().isEmpty() &&
            !location.getText().isEmpty() &&
            !startingTime.getText().isEmpty() &&
            !endingTime.getText().isEmpty();
  }


  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void close() {
    this.setVisible(false);
    this.dispose();
  }

  @Override
  public void outputEventDetails() {
    System.out.print("Event name: ");
    System.out.print(this.name.getText());
    System.out.print("\nLocation:\n");
    System.out.print(this.isOnline.getSelectedItem() + " ");
    System.out.print(this.location.getText());
    System.out.print("\nStarting day: ");
    System.out.print(this.startingDay.getSelectedItem());
    System.out.print("\nStarting time: ");
    System.out.print(this.startingTime.getText());
    System.out.print("\nEnding day: ");
    System.out.print(this.endingDay.getSelectedItem());
    System.out.print("\nEnding time: ");
    System.out.print(this.endingTime.getText());
    System.out.print("\nAvailable users: ");
    for (int item = 0; item < this.availUser.getSelectedValuesList().size(); item++) {
      System.out.print("\n" + this.availUser.getSelectedValuesList().get(item));
    }
    System.out.print("\n");
  }

  @Override
  public String observeEventNameFromEF() {
    return this.name.getText();
  }

  @Override
  public boolean observeIsOnlineFromEF() {
    return this.isOnline.getSelectedItem().toString().equals("Online");
  }

  @Override
  public String observeLocationFromEF() {
    return this.location.getText();
  }

  @Override
  public Day observeStartDayFromEF() {
    return (Day) this.startingDay.getSelectedItem();
  }

  @Override
  public int observeStartTimeFromEF() {
    if (this.startingTime.getText().isEmpty()) {
      return 0;
    } else {
      return Integer.parseInt(this.startingTime.getText());
    }
  }

  @Override
  public Day observeEndDayFromEF() {
    return (Day) this.endingDay.getSelectedItem();
  }

  @Override
  public int observeEndTimeFromEF() {
    if (this.endingTime.getText().isEmpty()) {
      return 0;
    } else {
      return Integer.parseInt(this.endingTime.getText());
    }
  }

  @Override
  public List<String> observeSelectedUsersFromEF() {
    List<String> selectedUsers = this.availUser.getSelectedValuesList();
    return selectedUsers;
  }

  @Override
  public String observeHostFromEF() {
    return this.selectedUsername;
  }

  @Override
  public int observeDurationFromSF() {
    return 0;
  }

  @Override
  public void addFeatures(IFeatures features) {
    createButton.addActionListener(evt ->
            features.createNewEvent(this.selectedUsername,
            this.observeEventNameFromEF(),
            this.observeLocationFromEF(),
            this.observeIsOnlineFromEF(),
            this.observeStartDayFromEF(),
            this.observeStartTimeFromEF(),
            this.observeEndDayFromEF(),
            this.observeEndTimeFromEF(),
            this.observeSelectedUsersFromEF()));
    modifyButton.addActionListener(evt ->
            features.modifyEvent(this.originalEvent,
            this.observeEventNameFromEF(),
            this.observeLocationFromEF(),
            this.observeIsOnlineFromEF(),
            this.observeStartDayFromEF(),
            this.observeStartTimeFromEF(),
            this.observeEndDayFromEF(),
            this.observeEndTimeFromEF(),
            this.observeSelectedUsersFromEF(),
            this.selectedUsername));
    removeButton.addActionListener(evt ->
            features.removeEvent(selectedUsername, this.originalEvent));
  }

}