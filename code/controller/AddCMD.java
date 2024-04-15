package controller;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.PlannerModel;
import model.User;
import model.Utils;
import view.EventView;
import view.PlannerView;

/**
 * This is the command for adding a schedule to the system using the File Chooser.
 */
class AddCMD implements IFeatures {

  private final PlannerModel model;
  private final PlannerView view;
  private final User host;
  private final ActionListener listener;

  /**
   * Constructs the AddCMD, which allows the controller to add a schedule to the system
   * from the file chooser.
   *
   * @param model    a PlannerModel
   * @param view     a PlannerView
   * @param host     a User
   * @param listener an ActionListener
   */
  AddCMD(PlannerModel model, PlannerView view, User host, ActionListener listener) {
    this.model = model;
    this.view = view;
    this.host = host;
    this.listener = listener;
  }

  @Override
  public void execute() {
    JFileChooser addFile = new JFileChooser();
    addFile.showSaveDialog(null);
    try {
      String pathAdd = addFile.getSelectedFile().getPath();
      System.out.println(pathAdd);
      model.addUser(Utils.readXML(pathAdd, model.getListOfUser()));
      view.reMakeView(host, this.listener);
    } catch (IllegalArgumentException | NullPointerException er) {
      JOptionPane.showMessageDialog((Component) view, er.getMessage());
    }
  }

  /**
   * Method returns null because this command does not involve the event view.
   *
   * @return null
   */
  @Override
  public EventView observeEventView() {
    return null;
  }
}
