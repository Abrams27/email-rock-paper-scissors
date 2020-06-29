package pl.uw.mim.jnp.zadanie2.camel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "camel.kafka")
public class CamelKafkaProperties {

  private String url;
  private CamelKafkaTopicsProperties topics;

}
