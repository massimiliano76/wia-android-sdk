package io.wia;

import java.util.List;

/**
 * Represents a user.
 */
public class WiaUser extends WiaResource {

  String id;

  String firstName;

  String lastName;

  String fullName;

  String emailAddress;

  /**
   * @return id of this user.
   */
  public String id() {
    return id;
  }

  /**
   * @return first name of this user.
   */
  public String firstName() {
    return firstName;
  }

  /**
   * @return last name of this user.
   */
  public String lastName() {
    return lastName;
  }

  /**
   * @return full name of this user.
   */
  public String fullName() {
    return fullName;
  }

  /**
   * @return email address of this user.
   */
  public String emailAddress() {
    return emailAddress;
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
