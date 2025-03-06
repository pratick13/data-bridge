package com.syncbridge.core.services;

import java.io.IOException;

public interface SchemaManagerService {

  void persistSchemaModel(
    String id,
    String name,
    String description,
    String ioType,
    byte[] data,
    String requester
  ) throws IOException;
}
