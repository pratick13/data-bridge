package com.syncbridge.services.api;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface SchemaManagerServiceApi {

    void uploadSchema(MultipartFile file);
    
}
