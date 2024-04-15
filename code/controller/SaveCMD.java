package controller;

import javax.swing.JFileChooser;

import model.PlannerModel;
import model.User;
import model.Utils;
import view.EventView;

/**
 * Tbis is the command for saving a schedule from the model. This action opens a JFileChooser
 * which is important to note when testing. Look at our testing notes to see how this is tested.
 */
class SaveCMD implements IFeatures {
  private final PlannerModel model;

  /**
   * Constructs a SaveCMD, which saves all of the schedules in the database into a path
   * chosen by the user through the File Chooser.
   *
   * @param model a PlannerModel
   */
  SaveCMD(PlannerModel model) {
    this.model = model;
  }

  @Override
  public void execute() {
    JFileChooser saveFile = new JFileChooser();
    saveFile.showSaveDialog(null);
    String pathSave = saveFile.getSelectedFile().getPath();
    System.out.println(pathSave);
    for (User u : model.getListOfUser()) {
      Utils.writeToFile(u, pathSave);
    }
  }

  @Override
  public EventView observeEventView() {
    return null;
  }
}
