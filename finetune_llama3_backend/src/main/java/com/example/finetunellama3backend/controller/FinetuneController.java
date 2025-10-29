package com.example.finetunellama3backend.controller;

import com.example.finetunellama3backend.dto.CreateJobRequest;
import com.example.finetunellama3backend.dto.JobStatusResponse;
import com.example.finetunellama3backend.model.FinetuneJob;
import com.example.finetunellama3backend.service.DatasetService;
import com.example.finetunellama3backend.service.FinetuneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Finetune job API for creating jobs and tracking status.
 */
@RestController
@RequestMapping("/api/finetune")
@Tag(name = "Finetune", description = "Create and manage finetuning jobs")
public class FinetuneController {

    private final FinetuneService finetuneService;
    private final DatasetService datasetService;

    public FinetuneController(FinetuneService finetuneService, DatasetService datasetService) {
        this.finetuneService = finetuneService;
        this.datasetService = datasetService;
    }

    // PUBLIC_INTERFACE
    @PostMapping(value = "/jobs", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create finetune job", description = "Creates a new finetuning job for a dataset and model")
    public JobStatusResponse create(@RequestBody CreateJobRequest request) {
        var dataset = datasetService.getEntity(request.getDatasetId());
        FinetuneJob job = finetuneService.createJob(dataset, request.getModelName());
        return finetuneService.getJob(job.getId());
    }

    // PUBLIC_INTERFACE
    @GetMapping("/jobs")
    @Operation(summary = "List jobs", description = "Lists all finetune jobs")
    public List<JobStatusResponse> list() {
        return finetuneService.listJobs();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/jobs/{id}")
    @Operation(summary = "Get job", description = "Gets a finetune job by id")
    public JobStatusResponse get(@PathVariable Long id) {
        return finetuneService.getJob(id);
    }

    // PUBLIC_INTERFACE
    @PostMapping("/jobs/{id}/cancel")
    @Operation(summary = "Cancel job", description = "Requests cancellation for a running or queued job")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        finetuneService.cancel(id);
        return ResponseEntity.accepted().build();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/jobs/{id}/logs")
    @Operation(summary = "Get logs", description = "Returns logs for the given job")
    public ResponseEntity<Resource> logs(@PathVariable Long id) {
        Resource logs = finetuneService.getLogs(id);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(logs);
    }
}
