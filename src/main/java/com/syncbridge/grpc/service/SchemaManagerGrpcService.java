package com.syncbridge.grpc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbridge.core.service.SchemaManagerService;
import com.syncbridge.gen.proto.SchemaManagerServiceGrpc;
import com.syncbridge.gen.proto.SchemaManagerServiceMessage.Response;
import com.syncbridge.gen.proto.SchemaManagerServiceMessage.UploadFileRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@GrpcService
public class SchemaManagerGrpcService extends SchemaManagerServiceGrpc.SchemaManagerServiceImplBase {

    private final Logger logger;
    private final SchemaManagerService schemaManagerService;

    @Autowired
    public SchemaManagerGrpcService(SchemaManagerService schemaManagerService) {
        this.schemaManagerService = schemaManagerService;
        this.logger = LoggerFactory.getLogger(SchemaManagerGrpcService.class);
    }

    @Override
    public void uploadSchema(UploadFileRequest request, StreamObserver<Response> responseObserver) {
        Response response;

        try {
            String fileName = request.getFileName();
            logger.info("Processing file {} ...", fileName);

            byte[] fileContent = request.getFileContent().toByteArray();
            InputStream iStream = new ByteArrayInputStream(fileContent);
            JsonNode jsonNode = new ObjectMapper().readTree(iStream);
            logger.info("Valid json schema uploaded");

            if (jsonNode != null) {
                schemaManagerService.processSchema(jsonNode);
            }
        } catch (Exception e) {
            logger.error("Error processing file: {}", e.getMessage());

            response = Response.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error processing file: " + e.getMessage())
                    .setDescription(e.toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            return;
        }

        response = Response.newBuilder()
                .setSuccess(true)
                .setMessage("Schema uploaded successfully")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
