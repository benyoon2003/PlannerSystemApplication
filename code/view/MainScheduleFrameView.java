package view;

import java.awt.event.ActionListener;

import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.IFeatures;
import model.Event;
import model.ReadOnlyPlannerModel;
import model.User;

/**
 * MainScheduleFrameView is a custom JFrame that implements PlannerView.
 * This is the main GUI view of the planner system in which a user can view any users
 * schedule and add or modify events in the system. This code cannot modify the model
 * and does not have an attached controller. Actions made in the view now will not
 * affect users at all.
 *
 * @implNote The mainPanel member variable is modified in reMakeView, thus it cannot be final. The
 *           selected User also changes depending on the JComboBox selection in MainBottomPanel.
 */
public class MainScheduleFrameView extends JFrame implements PlannerView {
  private final ReadOnlyPlannerModel model;
  private JPanel mainPanel;
  private WeekViewPanel planner;

  private final MainBottomPanel bottom;

  private User selected;

  private JMenuItem add;

  private JMenuItem save;


  /**
   * Creates a default main frame view that displays the schedule of the first
   * User in the database.
   *
   * @param model the given model of the planner system to be viewed.
   */
  public MainScheduleFrameView(ReadOnlyPlannerModel model) {
    super();
    setTitle("Main System View");
    setSize(800, 800);
    this.model = Objects.requireNonNull(model);
    this.mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    this.selected = this.model.getListOfUser().get(0);
    this.planner = new WeekViewPanel(this.model, this.selected);
    this.bottom = new MainBottomPanel(this.model, selected);
    this.mainPanel.add(this.planner);
    this.mainPanel.add(this.bottom);
    this.add(mainPanel);
    makeFileChooser();
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * This method creates the JMenu in the top left corner which leads to menu items
   * which would allow a user to add a calendar or save a calendar. At this point
   * these menu items cannot do much as there is no controller connected. The controller
   * would take the given files and give them to the model but the view should not do this.
   * The add calendar item opens a JfileChooser window where users can choose a file but
   * this does nothing.
   */
  private void makeFileChooser() {
    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("File");
    add = new JMenuItem("Add Calendar");
    save = new JMenuItem("Save Calendar");
    menu.add(add);
    menu.add(save);
    mb.add(menu);
    this.setJMenuBar(mb);
    add.setActionCommand("Add Menu Bar");
    save.setActionCommand("Save Menu Bar");
  }


  /**
   * This is a method that updates the view after a new user has been selected
   * from the JComboBox in the mainBottomPanel and displays the new users
   * schedule.
   *
   * <p>This is package protected as the MainBottomPanel uses this method to
   * update the entire frame but a client shouldn't be able to remake the
   * view outside the view.</p>
   *
   * @param selected is the user that is currently selected for the view.
   */
  public void reMakeView(User selected, ActionListener listener) {
    this.getContentPane().removeAll();
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    this.planner = new WeekViewPanel(this.model, selected);
    this.selected = selected;
    this.mainPanel.add(this.planner);
    this.mainPanel.add(this.bottom);
    this.add(mainPanel);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * This method is a method that this class inherits from the interface as
   * it's purpose it to render/display the view but this method is not needed
   * here as the JFrame automatically renders itself and displays itself.
   */
  @Override
  public void render(Event event, String cmd) {
    // Unimplemented
  }

  @Override
  public void addFeatures(IFeatures features) {
    this.planner.addFeatures
    this.bottom.setListener(listener);
    this.add.addActionListener(listener);
    this.save.addActionListener(listener);
  }

  /**
   * Observes the JComboBox to see if the user was switched.
   *
   * @return a User
   */
  public User observeUserSelectionBox() {
    return this.bottom.observeUserSelectionBox();
  }

}