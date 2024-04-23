package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.Objects;

import javax.swing.*;

import controller.IFeatures;
import model.IEvent;

/**
 * The EventRedPanel is a custom JPanel that implements MouseListener. It represents
 * the red panels, events, on the main scheduler's view and allows the user to click on
 * them to expose the event's details.
 *
 * @implNote This is package protected because there should not be any leakage of the Event panel
 *           information and this should be contained in the view.
 */
class EventRedPanel extends JPanel implements MouseListener {

  /**
   * Fields for the event panel. These fields set the panels that represent
   * an event via red rectangles on the week view.
   */

  private final IEvent event;

  private final int y;

  private final int width;

  private final int height;

  private final int horiz;

  private final String[] availUsers;

  private String selectedUsername;

  private IFeatures features;


  /**
   * Constructs an EventRedPanel with the given event, coordinates, and size specifications.
   *
   * @param e        an Event
   * @param x        the x coordinate as int
   * @param y        the y coordinate as int
   * @param width    the width as int
   * @param height   the height as int
   * @param horiz    the horizontal offset as int
   * @param horiz    the available users
   */

  EventRedPanel(IEvent e, int x, int y, int width, int height, int horiz, String[] availUsers,
                String selected, IFeatures features) {
    this.event = Objects.requireNonNull(e);
    this.setBounds(x, y, width, height);
    this.addMouseListener(this);
    this.y = y;
    this.width = width;
    this.height = height;
    this.horiz = horiz;
    this.availUsers = availUsers;
    this.selectedUsername = selected;
    this.features = features;

  }

  /**
   * This is the mouse click feature of the panel which allows a user
   * to click anywhere on the panel and open the EventView.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("CLICKED");
    EventView viewed = new EventFrameView(this.event,
            this.availUsers, this.selectedUsername);
    viewed.display();
    viewed.addFeatures(features);
  }

  /**
   * These are unused methods in the event listener interface that were not needed
   * for this functionality. These are empty stubs for that reason.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    // Unimplemented
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Unimplemented
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Unimplemented
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Unimplemented
  }

  /**
   * Paints the EventRedPanel automatically.
   *
   * @param g a Graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    super.paintComponent(g2d);
    drawLines(g2d);
    this.setBackground(Color.RED);
  }

  /**
   * This method is used to draw lines on the red panels so that when they
   * displayed on the week view the user can see with more accuracy the times
   * that these event are starting and ending. These line allows for the user
   * to conceptualize the times of these events.
   *
   * @param g2d the graphics library to draw with.
   */
  private void drawLines(Graphics2D g2d) {
    AffineTransform old = g2d.getTransform();
    int horizontalLineOffset = this.horiz;
    int offset = (this.horiz * 4) - (this.y % (this.horiz * 4));
    for (int line = -offset; line < this.height;
         line += horizontalLineOffset) {
      if ((line % (horizontalLineOffset * 4)) == 0) {
        g2d.setStroke(new BasicStroke(4));
      } else {
        g2d.setStroke(new BasicStroke(2));
      }
      g2d.setColor(Color.BLACK);
      g2d.drawLine(0, line + offset, this.width, line + offset);
    }
    g2d.setTransform(old);
  }
}