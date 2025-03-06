package com.syncbridge.core.db.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schemaless_data_models")
public class SchemalessDataModelEntity {

  @Id
  private String id;
  private String name;
  private String description;
  private String ioType;
  private String createdBy;
  private String createdDate;
  private String updatedBy;
  private String updatedDate;
  private org.bson.Document data;

  public SchemalessDataModelEntity() {
  }

  // For new schema
  public SchemalessDataModelEntity(
    String name,
    String description,
    String ioType,
    String createdBy,
    String createdDate,
    org.bson.Document data
  ) {
    this.name = name;
    this.description = description;
    this.ioType = ioType;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIoType() {
    return ioType;
  }

  public void setIoType(String ioType) {
    this.ioType = ioType;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
  }

  public org.bson.Document getData() {
    return data;
  }

  public void setData(org.bson.Document data) {
    this.data = data;
  }
}
