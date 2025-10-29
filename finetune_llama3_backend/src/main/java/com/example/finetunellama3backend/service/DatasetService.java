package com.example.finetunellama3backend.service;

import com.example.finetunellama3backend.dto.DatasetResponse;
import com.example.finetunellama3backend.model.Dataset;
import com.example.finetunellama3backend.repository.DatasetRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for datasets.
 */
@Service
public class DatasetService {

    private final DatasetRepository datasetRepository;
    private final StorageService storageService;

    public DatasetService(DatasetRepository datasetRepository, StorageService storageService) {
        this.datasetRepository = datasetRepository;
        this.storageService = storageService;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public DatasetResponse upload(String name, MultipartFile file) throws IOException {
        String path = storageService.storeDataset(file, name);
        Dataset ds = new Dataset(name, file.getOriginalFilename(), file.getContentType(), file.getSize(), path);
        ds = datasetRepository.save(ds);
        return toResponse(ds);
    }

    // PUBLIC_INTERFACE
    public List<DatasetResponse> list() {
        return datasetRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // PUBLIC_INTERFACE
    public Dataset getEntity(Long id) {
        return datasetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Dataset not found: " + id));
    }

    // PUBLIC_INTERFACE
    public Resource download(Long id) {
        Dataset ds = getEntity(id);
        return storageService.loadAsResource(ds.getStoragePath());
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void delete(Long id) throws IOException {
        Dataset ds = getEntity(id);
        datasetRepository.delete(ds);
        storageService.deletePath(ds.getStoragePath());
    }

    private DatasetResponse toResponse(Dataset ds) {
        return new DatasetResponse(ds.getId(), ds.getName(), ds.getOriginalFilename(), ds.getContentType(), ds.getSize(), ds.getCreatedAt());
    }
}
