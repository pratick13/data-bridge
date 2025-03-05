package com.syncbridge.core.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchemaManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(
            SchemaManagerServiceImpl.class);

    public void uploadSchema(JsonNode rootNode) {
        logger.info("Processing rootNode ...");


    }
}
