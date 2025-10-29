package com.example.finetunellama3backend.service;

import com.example.finetunellama3backend.dto.JobStatusResponse;
import com.example.finetunellama3backend.model.Dataset;
import com.example.finetunellama3backend.model.FinetuneJob;
import com.example.finetunellama3backend.repository.FinetuneJobRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for finetune jobs.
 */
@Service
public class FinetuneService {

    private final FinetuneJobRepository jobRepository;
    private final StorageService storageService;

    public FinetuneService(FinetuneJobRepository jobRepository, StorageService storageService) {
        this.jobRepository = jobRepository;
        this.storageService = storageService;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public FinetuneJob createJob(Dataset dataset, String modelName) {
        FinetuneJob job = new FinetuneJob(dataset, modelName);
        job.setStatus(FinetuneJob.Status.QUEUED);
        return jobRepository.save(job);
    }

    // PUBLIC_INTERFACE
    public JobStatusResponse getJob(Long id) {
        return toResponse(getEntity(id));
    }

    // PUBLIC_INTERFACE
    public List<JobStatusResponse> listJobs() {
        return jobRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void cancel(Long id) {
        FinetuneJob job = getEntity(id);
        if (job.getStatus() == FinetuneJob.Status.SUCCEEDED || job.getStatus() == FinetuneJob.Status.FAILED) {
            return;
        }
        job.setCancelRequested(true);
        jobRepository.save(job);
    }

    // PUBLIC_INTERFACE
    public Resource getLogs(Long id) {
        FinetuneJob job = getEntity(id);
        if (job.getLogsPath() == null) {
            throw new IllegalStateException("No logs yet for job " + id);
        }
        return storageService.loadAsResource(job.getLogsPath());
    }

    // PUBLIC_INTERFACE
    public FinetuneJob getEntity(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Job not found: " + id));
    }

    // PUBLIC_INTERFACE
    public FinetuneJob save(FinetuneJob job) {
        return jobRepository.save(job);
    }

    private JobStatusResponse toResponse(FinetuneJob job) {
        JobStatusResponse r = new JobStatusResponse();
        r.setId(job.getId());
        r.setModelName(job.getModelName());
        r.setStatus(job.getStatus());
        r.setDatasetId(job.getDataset().getId());
        r.setLogsPath(job.getLogsPath());
        r.setMetricsPath(job.getMetricsPath());
        r.setArtifactPath(job.getArtifactPath());
        r.setErrorMessage(job.getErrorMessage());
        r.setCreatedAt(job.getCreatedAt());
        r.setUpdatedAt(job.getUpdatedAt());
        return r;
    }
}
