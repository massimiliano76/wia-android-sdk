package io.wia;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a event.
 */
public class WiaEvent extends WiaResource {

  String id;

  String name;

  // /// The data of this Event
  // public var data: Any?

  /// The timestamp of this Event
  // @Expose(serialize = true, deserialize = true)
  // Date timestamp;

  /// The timestamp milliseconds of this Event
  @SerializedName("timestamp")
  private long timestampMs;

  /**
   * @return id of this event.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this event.
   */
  public String name() {
    return name;
  }

  /**
   * @return timestamp of this event.
   */
  public Date timestamp() {
    return new Date(timestampMs);
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this event
   */
  @Override public String toString() {
    return "WiaEvent{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + ", timestamp='" + timestamp() + '\''
        + '}';
  }
}
