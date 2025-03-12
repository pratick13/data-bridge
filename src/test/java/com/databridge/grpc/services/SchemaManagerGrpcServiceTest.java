package com.databridge.grpc.services;

import com.google.protobuf.ByteString;
import com.databridge.core.services.SchemaManagerService;
import com.databridge.gen.proto.SchemaManagerServiceMessage.Response;
import com.databridge.gen.proto.SchemaManagerServiceMessage.SchemaUploadRequest;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class SchemaManagerGrpcServiceTest {

  @Mock
  private SchemaManagerService schemaManagerService;

  @InjectMocks
  private SchemaManagerGrpcService schemaManagerGrpcService;

  @Test
  void testUploadSchemaSuccess() {
    when(schemaManagerService.persistSchemaModel(
      anyString(),
      anyString(),
      anyString(),
      anyString(),
      any(),
      anyString()
    )).thenReturn("test-schema-id");

    SchemaUploadRequest request = SchemaUploadRequest
      .newBuilder()
      .setName("test-schema-name")
      .setFileContent(ByteString.copyFromUtf8("{ \"key\": \"value\" }"))
      .build();
    StreamRecorder<Response> responseObserver = StreamRecorder.create();
    schemaManagerGrpcService.uploadSchema(request, responseObserver);

    assertNull(responseObserver.getError());
    List<Response> resList = responseObserver.getValues();
    assertEquals(1, resList.size());
    Response res = resList.get(0);
    assertTrue(res.getSuccess());
    assertEquals("Schema uploaded successfully", res.getMessage());
    assertEquals("{\"id\":\"test-schema-id\"}", res.getData().toStringUtf8());
  }

  @Test
  void testUploadSchemaFailure() {
    when(schemaManagerService.persistSchemaModel(
      anyString(),
      anyString(),
      anyString(),
      anyString(),
      any(),
      anyString()
    )).thenThrow(new RuntimeException("persistSchemaModel failed"));

    SchemaUploadRequest request = SchemaUploadRequest
      .newBuilder()
      .setName("test-schema-name")
      .setFileContent(ByteString.copyFromUtf8("{ \"key\": \"value\" }"))
      .build();
    StreamRecorder<Response> responseObserver = StreamRecorder.create();
    schemaManagerGrpcService.uploadSchema(request, responseObserver);

    assertNotNull(responseObserver.getError());
    assertEquals("INTERNAL: persistSchemaModel failed", responseObserver.getError().getMessage());
  }

}

