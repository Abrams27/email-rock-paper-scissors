package pl.uw.mim.jnp.zadanie2.camel.processors;

import lombok.experimental.UtilityClass;
import org.apache.camel.Message;

@UtilityClass
public class CamelMailSenderUtils {

  private final static String RECEIVER_HEADER_KEY = "To";
  private final static String SENDER_HEADER_KEY = "From";
  private final static String SUBJECT_HEADER_KEY = "Subject";

  public void setReceiverHeader(Message message, String receiverMail) {
    message.setHeader(RECEIVER_HEADER_KEY, receiverMail);
  }

  public void setSenderHeader(Message message, String senderMail) {
    message.setHeader(SENDER_HEADER_KEY, senderMail);
  }

  public void setSubjectHeader(Message message, String subject) {
    message.setHeader(SUBJECT_HEADER_KEY, subject);
  }

}
