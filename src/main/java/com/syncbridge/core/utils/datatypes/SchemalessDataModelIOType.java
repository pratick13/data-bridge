package com.syncbridge.core.utils.datatypes;

public enum SchemalessDataModelIOType {
  INPUT("I"),
  OUTPUT("O");

  private final String value;

  SchemalessDataModelIOType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
