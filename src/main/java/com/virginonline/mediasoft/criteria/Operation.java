package com.virginonline.mediasoft.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Operation {
  EQUALS("="),
  GREATER_THAN(">"),
  GRATER_THAN_OR_EQ(">="),
  LESS_THAN("<"),
  LESS_THAN_OR_EQ("<="),
  LIKE("%");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  Operation(String value) {
    this.value = value;
  }

  @JsonCreator
  public static Operation fromValue(String symbol) {
    for (Operation operation : Operation.values()) {
      if (operation.value.equals(symbol)) {
        return operation;
      }
    }
    throw new IllegalArgumentException("Unknown operation symbol: " + symbol);
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
