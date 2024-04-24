package view;

import java.awt.Color;

import controller.IFeatures;
import model.IEvent;

public class EventRedPanel extends EventPanel{

  EventRedPanel(IEvent e, int x, int y, int width, int height, int horiz,
                String[] availUsers, String selected, IFeatures features){
    super(e, x, y, width, height, horiz, availUsers,
            selected,features);
    this.setBackground(Color.RED);
  }

}
