package pl.uw.mim.jnp.zadanie2.camel.processors;


import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.models.GameStartMessage;
import pl.uw.mim.jnp.zadanie2.camel.utils.ObjectJsonMapper;

@Component
@AllArgsConstructor
public class CamelGameStartMailProcessor implements Processor {

  private final static String SENDER_MAIL_HEADER = "From";

  @Override
  public void process(Exchange exchange) {
    Message message = exchange.getIn();
    String startMessage = getStartMessage(message);

    message.setBody(startMessage);
  }

  private String getStartMessage(Message message) {
    String senderMail = getSenderMail(message);
    GameStartMessage gameStartMessage = buildStartMessage(senderMail);

    return ObjectJsonMapper.toJson(gameStartMessage);
  }

  private String getSenderMail(Message message) {
    return message
        .getHeader(SENDER_MAIL_HEADER)
        .toString();
  }

  private GameStartMessage buildStartMessage(String email) {
    return GameStartMessage.builder()
        .player(email)
        .build();
  }
}
