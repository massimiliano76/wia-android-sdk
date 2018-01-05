package io.wia;

import java.util.List;

/**
 * Represents a organisation.
 */
public class WiaOrganisation extends WiaResource {

  String id;

  String name;

  /**
   * @return id of this organisation.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this organisation.
   */
  public String name() {
    return name;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this organisation
   */
  @Override public String toString() {
    return "WiaOrganisation{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
