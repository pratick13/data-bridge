package com.syncbridge.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbridge.core.db.models.SchemalessDataModelEntity;
import com.syncbridge.core.db.repositories.SchemalessDataModelRepository;
import com.syncbridge.core.services.SchemaManagerService;
import com.syncbridge.core.utils.helper.TimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class SchemaManagerServiceImpl implements SchemaManagerService {
  private final Logger logger;
  private final SchemalessDataModelRepository schemalessDataModelRepository;
  private final TimeUtility timeUtility;

  @Autowired
  public SchemaManagerServiceImpl(
    SchemalessDataModelRepository schemalessDataModelRepository,
    TimeUtility timeUtility
  ) {
    this.schemalessDataModelRepository = schemalessDataModelRepository;
    this.logger = LoggerFactory.getLogger(SchemaManagerServiceImpl.class);
    this.timeUtility = timeUtility;
  }

  @Override
  public void persistSchemaModel(
    String id,
    String name,
    String description,
    String ioType,
    byte[] data,
    String requester
  ) throws IOException {
    logger.info("Uploading schema {} ...", name);

    InputStream iStream = new ByteArrayInputStream(data);
    JsonNode jsonNode = new ObjectMapper().readTree(iStream);

    logger.info("Valid json schema uploaded");


    if (id != null && !id.isEmpty()) {
      SchemalessDataModelEntity modelData =
        this.schemalessDataModelRepository.findById(id).orElse(null);

      if (modelData != null) {
        modelData.setName(name);
        modelData.setDescription(description);
        modelData.setIoType(ioType);
        modelData.setUpdatedBy(requester);
        modelData.setUpdatedDate(this.timeUtility.getCurrentTime());
        modelData.setData(jsonNode);
        this.schemalessDataModelRepository.save(modelData);
      }

    } else {
      SchemalessDataModelEntity modelData = new SchemalessDataModelEntity(
        name,
        description,
        ioType,
        requester,
        this.timeUtility.getCurrentTime(),
        jsonNode
      );
      this.schemalessDataModelRepository.save(modelData);
    }

    if (jsonNode != null) {
//      this.schemalessDataModelRepository.save();
    }

  }
}
