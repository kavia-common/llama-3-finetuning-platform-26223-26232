package com.example.finetunellama3backend.dto;

import com.example.finetunellama3backend.model.FinetuneJob;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public class JobStatusResponse {
    @Schema(description = "Job ID")
    private Long id;

    @Schema(description = "Base model name")
    private String modelName;

    @Schema(description = "Job status")
    private FinetuneJob.Status status;

    @Schema(description = "Dataset ID")
    private Long datasetId;

    @Schema(description = "Logs URL or path")
    private String logsPath;

    @Schema(description = "Metrics URL or path")
    private String metricsPath;

    @Schema(description = "Artifact URL or path (if succeeded)")
    private String artifactPath;

    @Schema(description = "Error message (if failed)")
    private String errorMessage;

    @Schema(description = "Created at")
    private Instant createdAt;

    @Schema(description = "Updated at")
    private Instant updatedAt;

    public Long getId() { return id; }
    public String getModelName() { return modelName; }
    public FinetuneJob.Status getStatus() { return status; }
    public Long getDatasetId() { return datasetId; }
    public String getLogsPath() { return logsPath; }
    public String getMetricsPath() { return metricsPath; }
    public String getArtifactPath() { return artifactPath; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public void setStatus(FinetuneJob.Status status) { this.status = status; }
    public void setDatasetId(Long datasetId) { this.datasetId = datasetId; }
    public void setLogsPath(String logsPath) { this.logsPath = logsPath; }
    public void setMetricsPath(String metricsPath) { this.metricsPath = metricsPath; }
    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
