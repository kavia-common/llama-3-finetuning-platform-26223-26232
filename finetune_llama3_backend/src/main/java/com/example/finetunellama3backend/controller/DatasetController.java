package com.example.finetunellama3backend.controller;

import com.example.finetunellama3backend.dto.DatasetResponse;
import com.example.finetunellama3backend.service.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Dataset API for uploading, listing, fetching, downloading, and deleting datasets.
 */
@RestController
@RequestMapping("/api/datasets")
@Tag(name = "Datasets", description = "Manage datasets used for finetuning")
public class DatasetController {

    private final DatasetService datasetService;

    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    // PUBLIC_INTERFACE
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload dataset", description = "Uploads a dataset file and stores it on disk")
    public DatasetResponse upload(@RequestParam("name") String name,
                                  @RequestPart("file") MultipartFile file) throws Exception {
        return datasetService.upload(name, file);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List datasets", description = "Returns all datasets")
    public List<DatasetResponse> list() {
        return datasetService.list();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get dataset", description = "Returns dataset details")
    public DatasetResponse get(@PathVariable Long id) {
        var ds = datasetService.getEntity(id);
        return new DatasetResponse(ds.getId(), ds.getName(), ds.getOriginalFilename(), ds.getContentType(), ds.getSize(), ds.getCreatedAt());
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}/download")
    @Operation(summary = "Download dataset", description = "Downloads the dataset file")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        var ds = datasetService.getEntity(id);
        Resource resource = datasetService.download(id);
        String filename = ds.getOriginalFilename() != null ? ds.getOriginalFilename() : "dataset.bin";
        String encoded = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encoded)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete dataset", description = "Removes dataset record and file")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        datasetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
