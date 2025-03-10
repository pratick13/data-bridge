package com.syncbridge.core.db.repositories;

import com.syncbridge.core.db.models.SchemalessDataModelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchemalessDataModelRepository extends MongoRepository<SchemalessDataModelEntity, String> {

  SchemalessDataModelEntity findByName(String name);

}
