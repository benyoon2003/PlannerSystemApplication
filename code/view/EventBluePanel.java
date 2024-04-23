package view;

import java.awt.*;

public class EventBluePanel extends EventPanelDecorator{

  EventPanel ep;

  EventBluePanel(EventPanel ep) {
    super(ep);
  }

  @Override
  void decorator() {
    ep.paintColor(Color.BLUE);
  }


}
