package pl.uw.mim.jnp.zadanie2.camel.models;

import lombok.Data;

@Data
public class GameResultMessage {

  private String opponent;
  private String opponentsHandSign;
  private String result;

}
