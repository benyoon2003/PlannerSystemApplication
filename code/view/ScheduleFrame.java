package view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;

import model.Day;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

/**
 * A ScheduleFrame is an EventView that allows the user to use a strategy
 * to schedule an event. The user only needs to insert the name of the event,
 * whether it is available online, the location, duration, and available users.
 */
public class ScheduleFrame extends JFrame implements EventView {

  private final String host;
  private final JPanel eventPanel;
  private JTextArea name;
  private JComboBox<String> isOnline;
  private JTextArea location;
  private JTextArea duration;
  private JList<String> availUser;
  private JButton scheduleButton;

  /**
   * Constructs a default EventFrameView that contains default components of an event and
   * sets the host according to the given username.
   *
   * @param availUsers the list of users available for selection
   */
  public ScheduleFrame(String[] availUsers) {
    this("",
            true, "", "", availUsers);
  }

  /**
   * This is the full constructor for a schedule frame where all the fields have values and also
   * sets up and places the different panels in this frame.
   *
   * @param eventName  the event name
   * @param isOnline   if the event is online
   * @param location   the location of the event
   * @param duration   how long the event is supposed to be
   * @param availUsers the available users
   */
  public ScheduleFrame(String eventName, boolean isOnline, String location,
                       String duration, String[] availUsers) {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.host = availUsers[0];
    this.eventPanel = new JPanel();
    this.eventPanel.setLayout(new BoxLayout(this.eventPanel, BoxLayout.Y_AXIS));

    makeNamePanel(eventName);
    makeLocationPanel(isOnline, location);
    makeDurationPanel(duration);
    makeAvailUserPanel(availUsers);
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
   * Creates the invited user panel which allows for modification of the list of invited users.
   * Rests on top of the main event panel.
   *
   * @param availUsers a String array
   */
  private void makeAvailUserPanel(String[] availUsers) {
    JPanel availUserLabelPanel = new JPanel();
    availUserLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel availUserLabel = new JLabel("Available users");
    JPanel availUserPanel = new JPanel(); // Panel for available users
    availUserPanel.setLayout(new BoxLayout(availUserPanel, BoxLayout.Y_AXIS));
    this.availUser = new JList<>(availUsers);
    this.availUser.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
    scheduleButton = new JButton("Schedule event");
    scheduleButton.setActionCommand("Schedule Event Finish");
    buttonPanel.add(scheduleButton);
    this.eventPanel.add(buttonPanel);
  }

  /**
   * Makes the jText area where the user can input the possible duration of the event.
   */
  private void makeDurationPanel(String duration) {
    JPanel endingTimePanel = new JPanel();
    JLabel endingTimeLabel = new JLabel("Duration:");
    this.duration = new JTextArea(duration, 1, 10);
    this.duration.setLineWrap(true);
    this.duration.setWrapStyleWord(true);
    endingTimePanel.add(endingTimeLabel);
    endingTimePanel.add(this.duration);
    this.eventPanel.add(endingTimePanel);
  }

  /**
   * Checks if any of the required text fields for an event are empty.
   *
   * @return a boolean
   */
  public boolean validInput() {
    return !name.getText().isEmpty() &&
            !location.getText().isEmpty() && !duration.getText().isEmpty();
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
    return null;
  }

  @Override
  public int observeStartTimeFromEF() {
    return 0;
  }

  @Override
  public Day observeEndDayFromEF() {
    return null;
  }

  @Override
  public int observeEndTimeFromEF() {
    return 0;
  }

  @Override
  public List<String> observeSelectedUsersFromEF() {
    return this.availUser.getSelectedValuesList();
  }

  @Override
  public String observeHostFromEF() {
    return this.host;
  }

  @Override
  public int observeDurationFromSF() {
    if (this.duration.getText().isEmpty()) {
      return 0;
    }
    return Integer.parseInt(this.duration.getText());
  }

  @Override
  public void addFeatures(ActionListener listener) {
    scheduleButton.addActionListener(listener);
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
    System.out.print("Create event: \n");
    System.out.print("Event name: ");
    System.out.print(this.name.getText());
    System.out.print("\nLocation:\n");
    System.out.print(this.isOnline.getSelectedItem() + " ");
    System.out.print(this.location.getText());
    System.out.print("\nAvailable users: ");
    for (int item = 0; item < this.availUser.getModel().getSize(); item++) {
      System.out.print("\n" + this.availUser.getModel().getElementAt(item));
    }
    System.out.print("\n");
  }
}
