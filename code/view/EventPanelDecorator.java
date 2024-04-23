package view;

import java.awt.*;

public abstract class EventPanelDecorator {
  EventPanel ep;

  EventPanelDecorator(EventPanel ep){
    this.ep = ep;
  }
  abstract void decorator();
}
