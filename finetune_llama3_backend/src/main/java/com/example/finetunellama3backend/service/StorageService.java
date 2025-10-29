package com.example.finetunellama3backend.service;

import com.example.finetunellama3backend.config.StorageConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Handles storage of datasets, logs, metrics, and artifacts on the filesystem.
 */
@Service
public class StorageService {

    private final Path root;

    public StorageService(StorageConfig config) throws IOException {
        this.root = Paths.get(config.getRoot()).toAbsolutePath().normalize();
        init();
    }

    private void init() throws IOException {
        Files.createDirectories(root);
        Files.createDirectories(root.resolve("datasets"));
        Files.createDirectories(root.resolve("jobs"));
        Files.createDirectories(root.resolve("artifacts"));
    }

    // PUBLIC_INTERFACE
    public String storeDataset(MultipartFile file, String logicalName) throws IOException {
        String folder = Instant.now().toEpochMilli() + "-" + UUID.randomUUID();
        Path datasetDir = root.resolve("datasets").resolve(folder);
        Files.createDirectories(datasetDir);
        String sanitized = Paths.get(file.getOriginalFilename() != null ? file.getOriginalFilename() : "dataset.data").getFileName().toString();
        Path target = datasetDir.resolve(sanitized);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        // Save meta file
        Files.writeString(datasetDir.resolve("meta.txt"),
                "name=" + logicalName + "\nfilename=" + sanitized + "\ncontentType=" + file.getContentType() + "\nsize=" + file.getSize(),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        return target.toString();
    }

    // PUBLIC_INTERFACE
    public Resource loadAsResource(String absolutePath) {
        return new FileSystemResource(absolutePath);
    }

    // PUBLIC_INTERFACE
    public void deletePath(String absolutePath) throws IOException {
        Path p = Paths.get(absolutePath);
        if (Files.exists(p)) {
            FileSystemUtils.deleteRecursively(p);
        }
    }

    // PUBLIC_INTERFACE
    public Path prepareJobDir(Long jobId) throws IOException {
        Path jobDir = root.resolve("jobs").resolve("job-" + jobId);
        Files.createDirectories(jobDir);
        return jobDir;
    }

    // PUBLIC_INTERFACE
    public Path writeLog(Long jobId, String content, boolean append) throws IOException {
        Path jobDir = prepareJobDir(jobId);
        Path log = jobDir.resolve("logs.txt");
        if (append && Files.exists(log)) {
            Files.writeString(log, content, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } else {
            Files.writeString(log, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        return log;
    }

    // PUBLIC_INTERFACE
    public Path writeMetrics(Long jobId, String json) throws IOException {
        Path jobDir = prepareJobDir(jobId);
        Path metrics = jobDir.resolve("metrics.json");
        Files.writeString(metrics, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return metrics;
    }

    // PUBLIC_INTERFACE
    public Path writeArtifact(Long jobId, String modelName) throws IOException {
        Path jobDir = prepareJobDir(jobId);
        Path artifact = jobDir.resolve(modelName + "-finetuned.bin");
        Files.writeString(artifact, "SIMULATED_ARTIFACT_FOR_JOB_" + jobId, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return artifact;
    }
}
