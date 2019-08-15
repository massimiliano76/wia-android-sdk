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
  String dataType;
  String imageType;
  String staticImage;
  String imageScaling;
  String stateKey;
  String stateValue;
  List<String> tableEvents;
  List<String> tableStateKeys;
  List<String> lineChartEvents;
  String color;
  String yMin;
  String yMax;

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

  /**
   * @return dataType of this widget.
   */
  public String dataType() {
    return dataType;
  }

  /**
   * @return imageType of this widget.
   */
  public String imageType() {
    return imageType;
  }

  /**
   * @return staticImage of this widget.
   */
  public String staticImage() {
    return staticImage;
  }

  /**
   * @return imageScaling of this widget.
   */
  public String imageScaling() {
    return imageScaling;
  }

  /**
   * @return stateKey of this widget.
   */
  public String stateKey() {
    return stateKey;
  }

  /**
   * @return stateValue of this widget.
   */
  public String stateValue() {
    return stateValue;
  }

  /**
   * @return tableEvents of this widget.
   */
  public List<String> tableEvents() {
    return tableEvents;
  }

  /**
   * @return tableStateKeys of this widget.
   */
  public List<String> tableStateKeys() {
    return tableStateKeys;
  }

  /**
   * @return lineChartEvents of this widget.
   */
  public List<String> lineChartEvents() {
    return lineChartEvents;
  }

  /**
   * @return color of this widget.
   */
  public String color() {
    return color;
  }

  /**
   * @return yMin of this widget.
   */
  public String yMin() {
    return yMin;
  }

  /**
   * @return yMax of this widget.
   */
  public String yMax() {
    return yMax;
  }
}
