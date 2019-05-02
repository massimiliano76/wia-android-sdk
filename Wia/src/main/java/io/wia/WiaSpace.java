package io.wia;

import java.util.List;

/**
 * Represents a space.
 */
public class WiaSpace extends WiaResource {

  String id;

  String name;

  WiaSpaceOwner owner;

  WiaSpaceAvatar avatar;

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
   * @return owner of this space.
   */
  public WiaSpaceOwner owner() {
    return owner;
  }

  /**
   * @return avatar of this space.
   */
  public WiaSpaceAvatar avatar() {
    return avatar;
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
