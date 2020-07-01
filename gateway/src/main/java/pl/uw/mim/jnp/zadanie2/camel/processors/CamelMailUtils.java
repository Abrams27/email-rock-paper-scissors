package pl.uw.mim.jnp.zadanie2.camel.processors;

import javax.mail.internet.MimeUtility;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.camel.Message;

@UtilityClass
public class CamelMailUtils {

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

  @SneakyThrows
  public String getSenderMail(Message message) {
    String messageString = message
        .getHeader(SENDER_HEADER_KEY)
        .toString();

    return MimeUtility.decodeText(messageString);
  }
}
