package io.wia;

import java.util.List;

/**
 * Represents an event query item.
 */
public class WiaEventQueryItem extends WiaResource {

  long avg;

  long count;

  long max;

  long min;

  long sum;

  String created_at;

  /**
   * @return avg of this event query item.
   */
  public long avg() {
    return avg;
  }

  /**
   * @return count of this event query item.
   */
  public long count() {
    return count;
  }

  /**
   * @return max of this event query item.
   */
  public long max() {
    return max;
  }

  /**
   * @return min of this event query item.
   */
  public long min() {
    return min;
  }

  /**
   * @return sum of this event query item.
   */
  public long sum() {
    return sum;
  }

  /**
   * @return created_at of this event query item.
   */
  public String created_at() {
    return created_at;
  }
}
