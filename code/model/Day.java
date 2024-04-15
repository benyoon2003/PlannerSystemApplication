package model;

/**
 * An enum to represent days of the week.
 */
public enum Day {

  Sunday("Sunday"), Monday("Monday"), Tuesday("Tuesday"), Wednesday("Wednesday"),
  Thursday("Thursday"), Friday("Friday"), Saturday("Saturday");

  protected final String day;

  /**
   * Constructs a Day enum.
   *
   * @param day a String
   */
  Day(String day) {
    this.day = day;
  }

  @Override
  public String toString() {
    return day;
  }
}
