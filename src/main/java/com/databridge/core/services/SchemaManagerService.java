package com.databridge.core.services;

public interface SchemaManagerService {

  String persistSchemaModel(
    String id,
    String name,
    String description,
    String ioType,
    byte[] data,
    String requester
  );

}
