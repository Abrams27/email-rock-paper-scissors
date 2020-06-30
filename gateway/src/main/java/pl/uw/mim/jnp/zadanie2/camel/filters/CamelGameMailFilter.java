package pl.uw.mim.jnp.zadanie2.camel.filters;

import org.apache.camel.Header;

public class CamelGameMailFilter {

  public final static String APPLY_FUNCTION_NAME = "apply";

  private final static String GAME_SUBJECT = "play";

  public boolean apply(@Header("Subject") String subject) {
    return subject.toLowerCase().equals(GAME_SUBJECT);
  }

}
