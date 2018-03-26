package io.wia;

/**
 * Represents a user invite.
 */
public class WiaSpaceUserInvite extends WiaResource {
  Boolean invited;

  /**
   * @return status of user invite
   */
  public Boolean invited() {
    return invited;
  }

  /**
   * Create a String from this object.
   * @return a String containing the response
   */
  @Override public String toString() {
    return "Boolean invited;{"
        + "invited='" + invited() + '\''
        + '}';
  }
}
