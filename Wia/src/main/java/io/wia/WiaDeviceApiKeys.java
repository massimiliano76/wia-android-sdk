package io.wia;

/**
 * Represents a device.
 */
public class WiaDeviceApiKeys extends WiaResource {

  String secretKey;

  long createdAt;

  /**
   * @return secret key of this device.
   */
  public String secretKey() {
    return secretKey;
  }

  /**
   * @return createdAt of this widget.
   */
  public long createdAt() {
    return createdAt;
  }

}
