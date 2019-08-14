package io.wia;

import java.util.List;

/**
 * Represents a device.
 */
public class WiaDevice extends WiaResource {

  String id;

  String name;

  List<WiaEvent> events;

  WiaLocation location;

  WiaDeviceType type;

  WiaProduct product;

  String serialNumber;

  Object state;

  /**
   * @return id of this device.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this device.
   */
  public String name() {
    return name;
  }

  /**
   * @return location of this device.
   */
  public WiaLocation location() {
    return location;
  }

  /**
   * @return events of this device.
   */
  public List<WiaEvent> events() {
    return events;
  }

  /**
   * @return device type of this device.
   */
  public WiaDeviceType deviceType() {
    return type;
  }

  /**
   * @return serial number of this device.
   */
  public String serialNumber() {
    return serialNumber;
  }

  /**
   * @return product of this device.
   */
  public WiaProduct product() {
    return product;
  }

  /**
   * @return state of this device.
   */
  public Object state() {
    return state;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this device
   */
  @Override public String toString() {
    return "WiaDevice{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
