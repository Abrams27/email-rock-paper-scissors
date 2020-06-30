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
    CamelMailSenderUtils.setReceiverHeader(message, receiverMail);
  }

  private void setSender(Message message) {
    String senderMail = camelMailProperties.getUsername();
    CamelMailSenderUtils.setSenderHeader(message, senderMail);
  }

  private void setSubject(Message message) {
    CamelMailSenderUtils.setSubjectHeader(message, SUBJECT_VALUE);
  }

}
