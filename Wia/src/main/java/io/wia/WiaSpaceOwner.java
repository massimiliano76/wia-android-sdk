package io.wia;

import java.util.List;

/**
 * Represents a space owner.
 */
public class WiaSpaceOwner extends WiaResource {

  WiaUser user;

  WiaOrganisation organisation;

  /**
   * @return user of this space owner.
   */
  public WiaUser user() {
    return user;
  }

  /**
   * @return organisation of this space owner.
   */
  public WiaOrganisation organisation() {
    return organisation;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this space
   */
  @Override public String toString() {
    return "WiaSpaceOwner{}";
  }
}
