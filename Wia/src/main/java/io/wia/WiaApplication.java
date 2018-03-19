package io.wia;

/**
 * Represents a application.
 */
public class WiaApplication extends WiaResource {

  String id;

  String name;

  /**
   * @return id of this application.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this application.
   */
  public String name() {
    return name;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this application
   */
  @Override public String toString() {
    return "WiaApplication{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
