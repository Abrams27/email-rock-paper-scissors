package pl.uw.mim.jnp.zadanie2.camel.filters;

import org.apache.camel.Body;

public class CamelGameStartMailFilter {

  public final static String APPLY_FUNCTION_NAME = "apply";

  private final static String GAME_START_MESSAGE = "START";

  public boolean apply(@Body String body) {
    return body.startsWith(GAME_START_MESSAGE);
  }
}
