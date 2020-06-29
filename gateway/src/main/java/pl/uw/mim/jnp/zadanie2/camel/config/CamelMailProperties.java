package pl.uw.mim.jnp.zadanie2.camel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "camel.mail")
public class CamelMailProperties {

  private String host;
  private String username;
  private String password;
  private Integer delay;

}
