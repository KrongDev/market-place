package com.marketplace.fileservice.service.strategy;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {
    String store(MultipartFile file);
    String store(java.io.InputStream inputStream, String filename);
    Resource load(String filename);
}
