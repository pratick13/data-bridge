package com.syncbridge.core.services.impl;

import com.syncbridge.core.db.models.SchemalessDataModelEntity;
import com.syncbridge.core.db.repositories.SchemalessDataModelRepository;
import com.syncbridge.core.services.SchemaManagerService;
import com.syncbridge.core.utils.helper.TimeUtility;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public String persistSchemaModel(
    String id,
    String name,
    String description,
    String ioType,
    byte[] data,
    String requester
  ) {
    Document document = Document.parse(new String(data));

    if (id != null && !id.isEmpty()) {
      SchemalessDataModelEntity modelData = this.schemalessDataModelRepository.findById(id).orElse(null);

      if (modelData != null) {
        modelData.setName(name);
        modelData.setDescription(description);
        modelData.setIoType(ioType);
        modelData.setUpdatedBy(requester);
        modelData.setUpdatedDate(this.timeUtility.getCurrentTime());
        modelData.setData(document);
        this.schemalessDataModelRepository.save(modelData);
      } else {
        String message = String.format("Schema model '%s' with id '%s' not found", name, id);
        this.logger.error(message);
        throw new NullPointerException(message);
      }
    } else {
      SchemalessDataModelEntity modelData = new SchemalessDataModelEntity(
        name,
        description,
        ioType,
        requester,
        this.timeUtility.getCurrentTime(),
        document
      );
      id = this.schemalessDataModelRepository.save(modelData).getId();
    }

    return id;
  }
}
