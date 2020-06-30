package pl.uw.mim.jnp.zadanie2.camel.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameMessage {

  private String player;
  private HandSign handSign;

}
