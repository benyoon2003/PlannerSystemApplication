package view;

import java.awt.*;

import controller.IFeatures;
import model.IEvent;

public class EventBluePanel implements EventPanel{

  EventRedPanel ep;

  EventBluePanel(EventRedPanel ep) {
    this.ep = ep;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    this.setBackground(Color.BLUE);
  }
}
