package io.wia;

import java.util.List;

/**
 * Represents a widget type.
 */
public class WiaWidgetType extends WiaResource {

  String id;

  String name;

  long updatedAt;

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
}
