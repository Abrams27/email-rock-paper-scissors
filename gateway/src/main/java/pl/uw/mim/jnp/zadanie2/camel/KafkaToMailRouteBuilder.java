package pl.uw.mim.jnp.zadanie2.camel;

import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.processors.CamelMatchmakingResultProcessor;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelKafkaRoute;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelMailRoute;

@Component
@AllArgsConstructor
public class KafkaToMailRouteBuilder extends RouteBuilder {

  private CamelKafkaRoute camelKafkaRoute;
  private CamelMailRoute camelMailRoute;
  private CamelMatchmakingResultProcessor camelMatchmakingResultProcessor;

  @Override
  public void configure() {
    configureSendMatchmakingResponse();
  }

  private void configureSendMatchmakingResponse() {
    String camelMailSendRouteString = camelMailRoute.sendRoute();
    String matchmakingCamelKafkaRouteString = camelKafkaRoute.matchmakingResultRoute();

    from(matchmakingCamelKafkaRouteString)
        .process(camelMatchmakingResultProcessor)
        .to(camelMailSendRouteString);
  }
}
