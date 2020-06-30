package pl.uw.mim.jnp.zadanie2.camel.config;

import lombok.Data;

@Data
public class CamelKafkaTopicsProperties {

  private String matchmaking;
  private String game;
  private String pairs;
  private String result;

}
