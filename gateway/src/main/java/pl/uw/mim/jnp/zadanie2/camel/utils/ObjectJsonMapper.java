package pl.uw.mim.jnp.zadanie2.camel.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectJsonMapper {

  private ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  public String toJson(Object object) {
    return objectMapper.writeValueAsString(object);
  }

  @SneakyThrows
  public <T> T fromJson(String json, Class<T> classType) {
    return objectMapper.readValue(json, classType);
  }

}
