package io.wia;

/**
 * Represents a user removal.
 */
public class WiaUserRemove extends WiaResource {
  Boolean removed;

  /**
   * @return status of user removal
   */
  public Boolean removed() {
    return removed;
  }

  /**
   * Create a String from this object.
   * @return a String containing the response
   */
  @Override public String toString() {
    return "WiaUserRemove invited;{"
        + "removed='" + removed() + '\''
        + '}';
  }
}
