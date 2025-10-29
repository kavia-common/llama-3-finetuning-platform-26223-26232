package com.example.finetunellama3backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public class ResultResponse {
    @Schema(description = "Result ID")
    private Long id;

    @Schema(description = "Job ID")
    private Long jobId;

    @Schema(description = "Dataset ID")
    private Long datasetId;

    @Schema(description = "Base model name")
    private String modelName;

    @Schema(description = "Artifact path")
    private String artifactPath;

    @Schema(description = "Metrics path")
    private String metricsPath;

    @Schema(description = "Created at")
    private Instant createdAt;

    public Long getId() { return id; }
    public Long getJobId() { return jobId; }
    public Long getDatasetId() { return datasetId; }
    public String getModelName() { return modelName; }
    public String getArtifactPath() { return artifactPath; }
    public String getMetricsPath() { return metricsPath; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public void setDatasetId(Long datasetId) { this.datasetId = datasetId; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }
    public void setMetricsPath(String metricsPath) { this.metricsPath = metricsPath; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
