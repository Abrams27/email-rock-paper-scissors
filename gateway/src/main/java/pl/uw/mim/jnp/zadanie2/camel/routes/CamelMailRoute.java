package pl.uw.mim.jnp.zadanie2.camel.routes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pl.uw.mim.jnp.zadanie2.camel.config.CamelMailProperties;

@Component
@AllArgsConstructor
public class CamelMailRoute {

  private CamelMailProperties camelMailProperties;

  public String route() {
    String host = camelMailProperties.getHost();
    String encodedUsername = encodeURL(camelMailProperties.getUsername());
    String encodedPassword = encodeURL(camelMailProperties.getPassword());
    int delay = camelMailProperties.getDelay();

    return String.format(
        "imaps://%s?username=%s&password=%s&delete=false&unseen=true&delay=%d",
        host, encodedUsername, encodedPassword, delay);
  }


  @SneakyThrows
  private String encodeURL(String string) {
    return URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
  }

}
