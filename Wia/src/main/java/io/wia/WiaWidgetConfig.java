package io.wia;

import java.util.List;

/**
 * Represents a widget config.
 */
public class WiaWidgetConfig extends WiaResource {

  String eventName;

  String eventDataField;

  String eventUnit;

  String commandSlug;

  String timePeriod;

  String aggregateFunction;

  /**
   * @return eventName of this widget.
   */
  public String eventName() {
    return eventName;
  }

  /**
   * @return eventDataField of this widget.
   */
  public String eventDataField() {
    return eventDataField;
  }

  /**
   * @return eventUnit of this widget.
   */
  public String eventUnit() {
    return eventUnit;
  }

  /**
   * @return commandSlug of this widget.
   */
  public String commandSlug() {
    return commandSlug;
  }

  /**
   * @return timePeriod of this widget.
   */
  public String timePeriod() {
    return timePeriod;
  }

  /**
   * @return aggregateFunction of this widget.
   */
  public String aggregateFunction() {
    return aggregateFunction;
  }
}
