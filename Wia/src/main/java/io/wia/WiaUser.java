package io.wia;

import java.util.List;

/**
 * Represents a user.
 */
public class WiaUser extends WiaResource {

  String id;

  String fullName;

  /**
   * @return id of this user.
   */
  public String id() {
    return id;
  }

  /**
   * @return full name of this user.
   */
  public String fullName() {
    return fullName;
  }

  /**
   * Create a String from this object.
   * @return a String containing id and full name
   */
  @Override public String toString() {
    return "WiaUser{"
        + "id='" + id() + '\''
        + ", fullName='" + fullName + '\''
        + '}';
  }
}
