package com.databridge.grpc.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.databridge.core.services.SchemaManagerService;
import com.databridge.gen.proto.SchemaManagerServiceGrpc;
import com.databridge.gen.proto.SchemaManagerServiceMessage.Response;
import com.databridge.gen.proto.SchemaManagerServiceMessage.SchemaUploadRequest;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@GrpcService
public class SchemaManagerGrpcService
  extends SchemaManagerServiceGrpc.SchemaManagerServiceImplBase {

  private final Logger logger = LoggerFactory.getLogger(SchemaManagerGrpcService.class);
  private SchemaManagerService schemaManagerService;

  @Autowired
  public void setSchemaManagerService(SchemaManagerService schemaManagerService) {
    this.schemaManagerService = schemaManagerService;
  }

  @Override
  public void uploadSchema(
    SchemaUploadRequest request,
    StreamObserver<Response> responseObserver
  ) {
    try {
      String name = request.getName();
      logger.info("Uploading schema for '{}' model ...", name);

      byte[] fileBytes = request.getFileContent().toByteArray();
      InputStream iStream = new ByteArrayInputStream(fileBytes);
      JsonNode jsonNode = new ObjectMapper().readTree(iStream);

      logger.info("Valid json schema uploaded for '{}' model", name);

      String id = null;

      if (jsonNode != null) {
        logger.info("Persisting json schema '{}' in datastore", name);

        id = this.schemaManagerService.persistSchemaModel(
          request.getId(),
          name,
          request.getDescription(),
          request.getIoType(),
          fileBytes,
          request.getRequester()
        );
      }

      Response response = Response.newBuilder()
                                  .setSuccess(true)
                                  .setData(ByteString.copyFromUtf8("{\"id\":\"" + id + "\"}"))
                                  .setMessage("Schema uploaded successfully")
                                  .build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      logger.error("Error processing file: {}", e.getMessage());
      logger.error(e.toString());
      responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
    }
  }
}
