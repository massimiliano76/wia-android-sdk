package io.wia;

import java.util.List;

/**
 * Represents an event query item.
 */
public class WiaEventQueryItem extends WiaResource {

  double avg;

  int count;

  double max;

  double min;

  double sum;

  String created_at;

  /**
   * @return avg of this event query item.
   */
  public double avg() {
    return avg;
  }

  /**
   * @return count of this event query item.
   */
  public int count() {
    return count;
  }

  /**
   * @return max of this event query item.
   */
  public double max() {
    return max;
  }

  /**
   * @return min of this event query item.
   */
  public double min() {
    return min;
  }

  /**
   * @return sum of this event query item.
   */
  public double sum() {
    return sum;
  }

  /**
   * @return created_at of this event query item.
   */
  public String created_at() {
    return created_at;
  }
}
