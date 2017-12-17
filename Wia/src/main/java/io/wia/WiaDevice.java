package io.wia;

import java.util.List;

/**
 * Represents a device.
 */
public class WiaDevice extends WiaResource {

  String id;

  String name;

  /**
   * @return id of this device.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this device.
   */
  public String name() {
    return name;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this device
   */
  @Override public String toString() {
    return "WiaDevice{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
