package io.wia;

import java.util.List;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a location.
 */
public class WiaLocation extends WiaResource {

  String id;

  Double latitude;

  Double longitude;

  Double altitude;

  // /// The timestamp of this location
  // public var timestamp: Date?

  /// The timestamp milliseconds of this Event
  @SerializedName("timestamp")
  private long timestampMs;

  /**
   * @return id of this location.
   */
  public String id() {
    return id;
  }

  /**
   * @return latitude of this location.
   */
  public Double latitude() {
    return latitude;
  }

  /**
   * @return longitude of this location.
   */
  public Double longitude() {
    return longitude;
  }

  /**
   * @return altitude of this location.
   */
  public Double altitude() {
    return altitude;
  }

  /**
   * @return timestamp of this event.
   */
  public Date timestamp() {
    return new Date(timestampMs);
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this location
   */
  @Override public String toString() {
    return "WiaLocation{"
        + "id='" + id() + '\''
        + ", latitude='" + Double.toString(latitude) + '\''
        + ", longitude='" + Double.toString(longitude) + '\''
        + ", timestamp='" + timestamp() + '\''
        + '}';
  }
}
