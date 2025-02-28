package com.syncbridge.services;

import java.io.IOException;
import java.io.InputStream;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbridge.gen.proto.SchemaManagerServiceGrpc;
import com.syncbridge.gen.proto.SchemaManagerServiceOuterClass.UploadFileRequest;
import com.syncbridge.gen.proto.SchemaManagerServiceOuterClass.Response;

public class SchemaManagerServiceImpl extends SchemaManagerServiceGrpc.SchemaManagerServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SchemaManagerServiceImpl.class);

    @Override
    public void uploadSchema(UploadFileRequest request, StreamObserver<Response> responseObserver) {

    }

    // public Response processSchemaFile(MultipartFile file) {
    //     try {
    //         String fileName = file.getOriginalFilename();
    //         JsonNode jsonNode = null;

    //         logger.info("Processing file %s...", fileName);

    //         try {
    //             InputStream iStream = file.getInputStream();
    //             jsonNode = new ObjectMapper().readTree(iStream);

    //             logger.info("Valid json schema uploaded");
    //         } catch (IOException e) {
    //             logger.error("Invalid json schema uploaded");

    //             return new Response.Builder(HttpStatus.BAD_REQUEST)
    //                 .message("Invalid json schema uploaded")
    //                 .error("Invalid file format")
    //                 .description("The file format is invalid, please upload a valid JSON or YAML schema file")
    //                 .build();
    //         }

    //         if (jsonNode != null) {
                
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error processing file: %s", e.getMessage());

    //         return new Response.Builder(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .message("Error processing file")
    //             .error(e.getMessage())
    //             .description(e.toString())
    //             .build();
    //     }

    //     return new Response.Builder(HttpStatus.NO_CONTENT).build();
    // }

}
