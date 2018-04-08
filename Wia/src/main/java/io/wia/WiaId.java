package io.wia;

import java.util.List;

/**
 * Represents an entity with an ID.
 */
public class WiaId extends WiaResource {

  String id;

  /**
   * @return id of this device.
   */
  public String id() {
    return id;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this device
   */
  @Override public String toString() {
    return "WiaId{"
        + "id='" + id() + '\''
        + '}';
  }
}
