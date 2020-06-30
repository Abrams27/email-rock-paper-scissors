package pl.uw.mim.jnp.zadanie2.camel.config;

import lombok.Data;

@Data
public class CamelMailHostsProperties {

  private String receive;
  private String send;

}
