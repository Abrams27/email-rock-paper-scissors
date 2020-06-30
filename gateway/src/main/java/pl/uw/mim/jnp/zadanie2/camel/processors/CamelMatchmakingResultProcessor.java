package pl.uw.mim.jnp.zadanie2.camel.processors;

import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.config.CamelMailProperties;
import pl.uw.mim.jnp.zadanie2.camel.models.MatchmakingResultMessage;
import pl.uw.mim.jnp.zadanie2.camel.utils.ObjectJsonMapper;

@Component
@AllArgsConstructor
public class CamelMatchmakingResultProcessor implements Processor {

  private final static String RECEIVER_HEADER_KEY = "To";
  private final static String SENDER_HEADER_KEY = "From";
  private final static String SUBJECT_HEADER_KEY = "Subject";
  private final static String SUBJECT_VALUE = "Rock paper scissors - Opponent found";

  private CamelMailProperties camelMailProperties;

  @Override
  public void process(Exchange exchange) {
    Message message = exchange.getIn();
    String messageBodyString = message.getBody().toString();

    MatchmakingResultMessage matchmakingResultMessage = getMatchmakingResultMessage(messageBodyString);
    String matchmakingResultMessageBody = getMatchmakingResultMessageBody(matchmakingResultMessage);

    setReceiver(message, matchmakingResultMessage);
    setSender(message);
    setSubject(message);

    message.setBody(matchmakingResultMessageBody);
  }

  private MatchmakingResultMessage getMatchmakingResultMessage(String json) {
    return ObjectJsonMapper.fromJson(json, MatchmakingResultMessage.class);
  }

  private String getMatchmakingResultMessageBody(MatchmakingResultMessage matchmakingResultMessage) {
    String opponentEmail = matchmakingResultMessage.getPlayer2();

    return String
        .format("Opponent found, his name: %s.\nSend now Rock(kamień), Paper(papier), Scissors(nożyce) to play.", opponentEmail);
  }

  private void setReceiver(Message message, MatchmakingResultMessage matchmakingResultMessage) {
    String receiverMail = matchmakingResultMessage.getPlayer1();
    setReceiverHeader(message, receiverMail);
  }

  private void setReceiverHeader(Message message, String receiverMail) {
    message.setHeader(RECEIVER_HEADER_KEY, receiverMail);
  }

  private void setSender(Message message) {
    String senderMail = camelMailProperties.getUsername();
    setSenderHeader(message, senderMail);
  }

  private void setSenderHeader(Message message, String senderMail) {
    message.setHeader(SENDER_HEADER_KEY, senderMail);
  }

  private void setSubject(Message message) {
    setSubjectHeader(message, SUBJECT_VALUE);
  }

  private void setSubjectHeader(Message message, String subject) {
    message.setHeader(SUBJECT_HEADER_KEY, subject);
  }

}
