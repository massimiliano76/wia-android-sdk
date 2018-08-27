package io.wia;

import java.util.List;

/**
 * Represents a widget.
 */
public class WiaWidget extends WiaResource {

  String id;

  String name;

  Boolean isPublic;
  
  long createdAt;

  long updatedAt;

  WiaWidgetConfig config;

  WiaWidgetType type;

  /**
   * @return id of this widget.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this widget.
   */
  public String name() {
    return name;
  }

  /**
   * @return updatedAt of this widget.
   */
  public long updatedAt() {
    return updatedAt;
  }

  /**
   * @return createdAt of this widget.
   */
  public long createdAt() {
    return createdAt;
  }

  /**
   * @return config of this widget.
   */
  public WiaWidgetConfig config() {
    return config;
  }

  /**
   * @return type of this widget.
   */
  public WiaWidgetType widgetType() {
    return type;
  }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this widget
   */
  @Override public String toString() {
    return "WiaWidget{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }
}
