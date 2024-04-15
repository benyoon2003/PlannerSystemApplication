package model;

/**
 * An enum to represent tags in an XML file.
 */
public enum Tag {

  name("name"), time("time"), startDay("start-day"), start("start"), endDay("end-day"),
  end("end"), location("location"), online("online"), place("place"), users("users"), uid("uid");

  protected final String tag;

  /**
   * Constructs a tag enum.
   *
   * @param tag a String
   */
  Tag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return tag;
  }
}
