package view;

import java.awt.*;

import controller.IFeatures;
import model.IEvent;

public class EventBluePanel extends EventPanel {



  EventBluePanel(IEvent e, int x, int y, int width, int height, int horiz,
                 String[] availUsers, String selected, IFeatures features){
    super(e, x, y, width, height, horiz, availUsers,
            selected,features);
    this.setBackground(new Color(51,204,255));
  }


}
