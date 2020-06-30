package pl.uw.mim.jnp.zadanie2.camel.filters;

import org.apache.camel.Header;

public class CamelGameMailFilter {

  public final static String APPLY_FUNCTION_NAME = "apply";

  private final static String GAME_SUBJECT_EN = "play";
  private final static String GAME_SUBJECT_PL = "graj";

  public boolean apply(@Header("Subject") String subject) {
    return isSubjectEqualsto(subject, GAME_SUBJECT_EN)
        || isSubjectEqualsto(subject, GAME_SUBJECT_PL);
  }

  private boolean isSubjectEqualsto(String subject, String pattern) {
    return subject.toLowerCase().equals(pattern);
  }

}
