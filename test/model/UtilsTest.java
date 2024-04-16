package model;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Day;
import model.Event;
import model.User;
import model.Utils;

import static org.junit.Assert.assertEquals;

/**
 * This is the testing suite for the utils class.
 */
public class UtilsTest {
  private PlannerModel example;

  private PlannerModel example2;

  private User ben;

  private User spongebob;

  private void exampleNuPlanner() {
    this.example = new NuPlanner(new ArrayList<User>());
    ben = this.example.addUser("Ben");
    User nico = this.example.addUser("Nico");
    IEvent e1 = this.example.createEvent("Ben", "Working on OOD", "Snell", false,
            Day.Monday, 2000, Day.Thursday, 2059, List.of("Nico"));

  }

  private void exampleNuPlanner2() {
    this.example2 = new NuPlanner(new ArrayList<>());
    User lucia = this.example2.addUser("Lucia");
    User patrick = this.example2.addUser("Patrick");
    spongebob = this.example2.addUser("Spongebob");
    User squidward = this.example2.addUser("Squidward");
    IEvent e2 = this.example2.createEvent("Lucia", "grading exams", "home", true,
            Day.Monday, 0, Day.Monday, 1, List.of("Squidward"));
    IEvent e3 = this.example2.createEvent("Patrick", "eating", "Krusty Krab", false,
            Day.Tuesday, 500, Day.Thursday, 10, List.of("Squidward", "Spongebob"));
    IEvent e4 = this.example2.createEvent("Spongebob", "flipping patties", "Krusty Krab",
            false, Day.Friday, 600, Day.Saturday, 700, List.of("Patrick"));
  }


  @Test
  public void testWriteAndReadingXMLFile() {
    exampleNuPlanner();
    Utils.writeToFile(ben, "");
    assertEquals(Utils.readXML("Ben", example.getListOfUser()), ben);

    exampleNuPlanner2();
    Utils.writeToFile(spongebob, "");
    assertEquals(Utils.readXML("Spongebob", example2.getListOfUser()), spongebob);
  }
}
