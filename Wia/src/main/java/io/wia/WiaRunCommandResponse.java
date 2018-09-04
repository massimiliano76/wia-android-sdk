package io.wia;

import java.util.List;

/**
 * Represents a command.
 */
public class WiaRunCommandResponse extends WiaResource {

  String running;

  /**
   * @return running of this command.
   */
  public String running() {
    return running;
  }
}