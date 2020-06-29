package pl.uw.mim.jnp.zadanie2.camel.filters;

import org.apache.camel.Body;

public class CamelGameMailFilter {

  public final static String APPLY_FUNCTION_NAME = "apply";

  private final static String GAME_MESSAGE = "PLAY";

  public boolean apply(@Body String body) {
    return body.startsWith(GAME_MESSAGE);
  }
}
