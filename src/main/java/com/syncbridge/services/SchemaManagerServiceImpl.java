package com.syncbridge.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbridge.gen.proto.SchemaManagerServiceGrpc;
import com.syncbridge.gen.proto.SchemaManagerServiceMessage.Response;
import com.syncbridge.gen.proto.SchemaManagerServiceMessage.UploadFileRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@GrpcService
public class SchemaManagerServiceImpl extends SchemaManagerServiceGrpc.SchemaManagerServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SchemaManagerServiceImpl.class);

    @Override
    public void uploadSchema(UploadFileRequest request, StreamObserver<Response> responseObserver) {
        Response response = null;
        try {
            String fileName = request.getFileName();
            byte[] fileContent = request.getFileContent().toByteArray();
            JsonNode jsonNode = null;

            logger.info("Processing file {} ...", fileName);

            try {
                InputStream iStream = new ByteArrayInputStream(fileContent);
                jsonNode = new ObjectMapper().readTree(iStream);

                logger.info("Valid json schema uploaded");
            } catch (IOException e) {
                logger.error("Invalid json schema uploaded");

                response = Response.newBuilder().setSuccess(false).setMessage("Invalid json schema uploaded").setDescription("The file format is invalid, please upload a valid JSON or YAML schema file").build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();

                return;
            }

            if (jsonNode != null) {

            }
        } catch (Exception e) {
            logger.error("Error processing file: {}", e.getMessage());

            response = Response.newBuilder().setSuccess(false).setMessage("Error processing file: " + e.getMessage()).setDescription(e.toString()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            return;
        }

        response = Response.newBuilder().setSuccess(true).setMessage("Schema uploaded successfully").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
