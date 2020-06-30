package pl.uw.mim.jnp.zadanie2.camel.routes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.config.CamelKafkaProperties;

@Component
@AllArgsConstructor
public class CamelKafkaRoute {

  private CamelKafkaProperties camelKafkaProperties;

  public String matchmakingRoute() {
    String matchmakingTopic = camelKafkaProperties.getTopics().getMatchmaking();

    return buildKafkaRouteWithTopic(matchmakingTopic);
  }

  public String gameRoute() {
    String gameTopic = camelKafkaProperties.getTopics().getGame();

    return buildKafkaRouteWithTopic(gameTopic);
  }

  public String matchmakingResultRoute() {
    String matchmakingResultTopic = camelKafkaProperties.getTopics().getPairs();

    return buildKafkaRouteWithTopic(matchmakingResultTopic);
  }

  public String gameResultRoute() {
    String gameResultTopic = camelKafkaProperties.getTopics().getResult();

    return buildKafkaRouteWithTopic(gameResultTopic);
  }

  private String buildKafkaRouteWithTopic(String topic) {
    String url = camelKafkaProperties.getUrl();

    return buildKafkaRouteWithTopicAndUrl(topic, url);
  }

  private String buildKafkaRouteWithTopicAndUrl(String topic, String url) {
    return String.format("kafka:%s?brokers=%s", topic, url);
  }

}
