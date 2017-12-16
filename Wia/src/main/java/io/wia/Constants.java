package io.wia;

import java.util.Locale;

/**
 * This class holds specific constants.
 */
public class Constants {
  /**
   * DO NOT CALL THIS CONSTRUCTOR
   *
   * Calling this constructor will result in an {@link AssertionError},
   * since no instances of this class shall exist.
   */
  private Constants() {
    throw new AssertionError();
  }

  static final Locale LOCALE = Locale.US;

  static final String CHARSET = "UTF-8";

  static final String SCHEME = "https";
}
