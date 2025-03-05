package com.syncbridge.core.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface SchemaManagerService {

    void processSchema(JsonNode rootNode);

}
