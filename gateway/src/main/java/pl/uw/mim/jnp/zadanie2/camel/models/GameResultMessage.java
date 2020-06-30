package pl.uw.mim.jnp.zadanie2.camel.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResultMessage {

  private String opponent;
  private HandSign opponentsHandSign;
  private String result;

}
