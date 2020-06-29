package pl.uw.mim.jnp.zadanie2.camel;

import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.filters.CamelGameMailFilter;
import pl.uw.mim.jnp.zadanie2.camel.filters.CamelGameStartMailFilter;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelKafkaRoute;
import pl.uw.mim.jnp.zadanie2.camel.routes.CamelMailRoute;

@Component
@AllArgsConstructor
public class MailToKafkaRouteBuilder extends RouteBuilder {

  private CamelMailRoute camelMailRoute;
  private CamelKafkaRoute camelKafkaRoute;

  @Override
  public void configure() {
    String camelMailRouteString = camelMailRoute.route();
    String matchmakingCamelKafkaRouteString = camelKafkaRoute.matchmakingRoute();
    String gamecamelKafkaRouteString = camelKafkaRoute.gameRoute();

    from(camelMailRouteString)
        .filter()
        .method(CamelGameStartMailFilter.class, CamelGameStartMailFilter.APPLY_FUNCTION_NAME)
          .to(matchmakingCamelKafkaRouteString)
        .end()
        .filter()
        .method(CamelGameMailFilter.class, CamelGameMailFilter.APPLY_FUNCTION_NAME)
          .to(gamecamelKafkaRouteString)
        .end();
  }

}
