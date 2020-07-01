package pl.uw.mim.jnp.zadanie2.camel.processors;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.config.CamelMailProperties;
import pl.uw.mim.jnp.zadanie2.camel.models.GameResultMessage;
import pl.uw.mim.jnp.zadanie2.camel.utils.ObjectJsonMapper;

@Component
@AllArgsConstructor
public class CamelGameResultProcessor implements Processor {

  private final static String SUBJECT_VALUE = "Rock paper scissors - Game result";
  private final static Map<String, String> SHORT_TO_LONG_HAND_SIGN_FORM = Map.of(
      "R", "ROCK",
      "P", "PAPER",
      "S", "SCISSORS"
  );

  private CamelMailProperties camelMailProperties;

  @Override
  public void process(Exchange exchange) {
    Message message = exchange.getIn();
    String messageBodyString = message.getBody().toString();

    GameResultMessage gameResultMessage = getGameResultMessage(messageBodyString);
    String matchmakingResultMessageBody = getGameResultMessageBody(gameResultMessage);

    setReceiver(message, gameResultMessage);
    setSender(message);
    setSubject(message);

    message.setBody(matchmakingResultMessageBody);
  }

  private GameResultMessage getGameResultMessage(String json) {
    return ObjectJsonMapper.fromJson(json, GameResultMessage.class);
  }

  private String getGameResultMessageBody(GameResultMessage gameResultMessage) {
    String opponentEmail = gameResultMessage.getOpponent();
    String opponentHandSign = gameResultMessage.getOpponentHandSign();
    String longOpponentHandSign = mapHAndSignToLongForm(opponentHandSign);
    String result = gameResultMessage.getResult();

    return String
        .format("You played against: %s. \n Opponent showed: %s.\n%s",
            opponentEmail, longOpponentHandSign, result);
  }

  private String mapHAndSignToLongForm(String shortHandSign) {
    return SHORT_TO_LONG_HAND_SIGN_FORM.getOrDefault(shortHandSign, "");
  }

  private void setReceiver(Message message, GameResultMessage gameResultMessage) {
    String receiverMail = gameResultMessage.getPlayer();
    CamelMailUtils.setReceiverHeader(message, receiverMail);
  }

  private void setSender(Message message) {
    String senderMail = camelMailProperties.getUsername();
    CamelMailUtils.setSenderHeader(message, senderMail);
  }

  private void setSubject(Message message) {
    CamelMailUtils.setSubjectHeader(message, SUBJECT_VALUE);
  }

}
