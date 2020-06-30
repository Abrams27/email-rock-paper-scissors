package pl.uw.mim.jnp.zadanie2.camel.models;

import java.util.List;
import java.util.Map;

public enum HandSign {
  PAPER("paper"),
  ROCK("rock"),
  SCISSORS("scissors");

  private final static Map<String, String> POLISH_TO_ENGLISH_MAP = Map.of(
      "papier", "paper",
      "kamień", "rock",
      "kamien", "rock",
      "nożyce", "scissors",
      "nozyce", "scissors"
  );

  private String value;

  HandSign(String value) {
    this.value = value;
  }

  public static HandSign fromString(String value) {
    String mappedValue = mapPolishToEnglishValue(value);

    return List.of(HandSign.values()).stream()
        .filter(o -> o.value.equalsIgnoreCase(mappedValue))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

  private static String mapPolishToEnglishValue(String value) {
    return POLISH_TO_ENGLISH_MAP.getOrDefault(value.toLowerCase(), value.toLowerCase());
  }

}
