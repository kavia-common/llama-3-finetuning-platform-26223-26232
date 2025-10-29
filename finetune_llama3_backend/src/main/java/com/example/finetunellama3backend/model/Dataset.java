package com.example.finetunellama3backend.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Dataset entity represents an uploaded dataset stored on disk.
 */
@Entity
@Table(name = "datasets")
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String originalFilename;
    private String contentType;
    private long size;

    @Column(nullable = false, unique = true)
    private String storagePath;

    private Instant createdAt = Instant.now();

    public Dataset() {}

    public Dataset(String name, String originalFilename, String contentType, long size, String storagePath) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.storagePath = storagePath;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getOriginalFilename() { return originalFilename; }

    public String getContentType() { return contentType; }

    public long getSize() { return size; }

    public String getStoragePath() { return storagePath; }

    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public void setSize(long size) { this.size = size; }

    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
