package com.marketplace.fileservice.service;

import com.marketplace.fileservice.dto.FileDto;
import com.marketplace.fileservice.service.strategy.StorageStrategy;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FileService {

    private final StorageStrategy storageStrategy;

    public FileDto.UploadResponse uploadFile(MultipartFile file) {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            storageStrategy.store(file.getInputStream(), filename);
        } catch (java.io.IOException e) {
             throw new RuntimeException("Failed to read file input stream", e);
        }
        
        return buildResponse(filename);
    }

    public String saveContent(String content, String filename) {
        try {
            return storageStrategy.store(new java.io.ByteArrayInputStream(content.getBytes()), filename);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save content", e);
        }
    }

    private FileDto.UploadResponse buildResponse(String filename) {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/")
                .path(filename)
                .toUriString();

        return FileDto.UploadResponse.builder()
                .filename(filename)
                .url(fileDownloadUri)
                .build();
    }

    public Resource loadFile(String filename) {
        return storageStrategy.load(filename);
    }
}
