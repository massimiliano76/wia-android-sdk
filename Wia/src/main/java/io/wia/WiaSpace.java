package io.wia;

import java.util.List;

/**
 * Represents a space.
 */
public class WiaSpace extends WiaResource {

  String id;

  String name;

  /**
   * @return id of this space.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this space.
   */
  public String name() {
    return name;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this space
   */
  @Override public String toString() {
    return "WiaSpace{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
