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

  @Override
  public void process(Exchange exchange) {
    Message message = exchange.getIn();
    String startMessage = getStartMessage(message);

    message.setBody(startMessage);
  }

  private String getStartMessage(Message message) {
    String senderMail = CamelMailUtils.getSenderMail(message);
    GameStartMessage gameStartMessage = buildStartMessage(senderMail);

    return ObjectJsonMapper.toJson(gameStartMessage);
  }

  private GameStartMessage buildStartMessage(String email) {
    return GameStartMessage.builder()
        .player(email)
        .build();
  }

}
