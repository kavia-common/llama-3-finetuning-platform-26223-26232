package com.example.finetunellama3backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public class DatasetResponse {
    @Schema(description = "Dataset ID")
    private Long id;

    @Schema(description = "Dataset name")
    private String name;

    @Schema(description = "Original filename")
    private String originalFilename;

    @Schema(description = "MIME content type")
    private String contentType;

    @Schema(description = "Size in bytes")
    private long size;

    @Schema(description = "Creation timestamp")
    private Instant createdAt;

    public DatasetResponse() {}

    public DatasetResponse(Long id, String name, String originalFilename, String contentType, long size, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getOriginalFilename() { return originalFilename; }

    public String getContentType() { return contentType; }

    public long getSize() { return size; }

    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public void setSize(long size) { this.size = size; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
