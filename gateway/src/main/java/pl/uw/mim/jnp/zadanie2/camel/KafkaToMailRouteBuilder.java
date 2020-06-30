package pl.uw.mim.jnp.zadanie2.camel;

import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.processors.CamelGameResultProcessor;
import pl.uw.mim.jnp.zadanie2.camel.processors.CamelMatchmakingResultProcessor;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelKafkaRoute;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelMailRoute;

@Component
@AllArgsConstructor
public class KafkaToMailRouteBuilder extends RouteBuilder {

  private CamelKafkaRoute camelKafkaRoute;
  private CamelMailRoute camelMailRoute;
  private CamelMatchmakingResultProcessor camelMatchmakingResultProcessor;
  private CamelGameResultProcessor camelGameResultProcessor;

  @Override
  public void configure() {
    configureSendMatchmakingResponse();
    configureSendGameResult();
  }

  private void configureSendMatchmakingResponse() {
    String camelMailSendRouteString = camelMailRoute.sendRoute();
    String matchmakingResultCamelKafkaRouteString = camelKafkaRoute.matchmakingResultRoute();

    from(matchmakingResultCamelKafkaRouteString)
        .process(camelMatchmakingResultProcessor)
        .to(camelMailSendRouteString);
  }

  private void configureSendGameResult() {
    String camelMailSendRouteString = camelMailRoute.sendRoute();
    String gameResultCamelKafkaRouteString = camelKafkaRoute.gameResultRoute();

    from(gameResultCamelKafkaRouteString)
        .process(camelGameResultProcessor)
        .to(camelMailSendRouteString);
  }
}
