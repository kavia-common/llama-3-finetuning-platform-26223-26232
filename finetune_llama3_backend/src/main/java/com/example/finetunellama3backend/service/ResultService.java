package com.example.finetunellama3backend.service;

import com.example.finetunellama3backend.dto.ResultResponse;
import com.example.finetunellama3backend.model.FinetuneResult;
import com.example.finetunellama3backend.repository.FinetuneResultRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for results exposure.
 */
@Service
public class ResultService {

    private final FinetuneResultRepository resultRepository;
    private final StorageService storageService;

    public ResultService(FinetuneResultRepository resultRepository, StorageService storageService) {
        this.resultRepository = resultRepository;
        this.storageService = storageService;
    }

    // PUBLIC_INTERFACE
    public List<ResultResponse> list() {
        return resultRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // PUBLIC_INTERFACE
    public Resource downloadArtifact(Long id) {
        FinetuneResult r = resultRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Result not found: " + id));
        return storageService.loadAsResource(r.getArtifactPath());
    }

    // PUBLIC_INTERFACE
    public FinetuneResult save(FinetuneResult result) {
        return resultRepository.save(result);
    }

    private ResultResponse toResponse(FinetuneResult r) {
        ResultResponse dto = new ResultResponse();
        dto.setId(r.getId());
        dto.setJobId(r.getJob().getId());
        dto.setDatasetId(r.getDataset().getId());
        dto.setModelName(r.getModelName());
        dto.setArtifactPath(r.getArtifactPath());
        dto.setMetricsPath(r.getMetricsPath());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}
