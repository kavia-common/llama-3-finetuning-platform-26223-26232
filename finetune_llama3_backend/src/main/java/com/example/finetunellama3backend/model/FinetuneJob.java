package com.example.finetunellama3backend.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * FinetuneJob represents a simulated training job linked to a dataset.
 */
@Entity
@Table(name = "finetune_jobs")
public class FinetuneJob {

    public enum Status {
        QUEUED,
        RUNNING,
        SUCCEEDED,
        FAILED,
        CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelName;

    @Enumerated(EnumType.STRING)
    private Status status = Status.QUEUED;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dataset dataset;

    private String logsPath;      // text logs
    private String metricsPath;   // json metrics
    private String artifactPath;  // trained model artifact (simulated)

    private String errorMessage;

    private boolean cancelRequested = false;

    public FinetuneJob() {}

    public FinetuneJob(Dataset dataset, String modelName) {
        this.dataset = dataset;
        this.modelName = modelName;
    }

    public Long getId() { return id; }

    public String getModelName() { return modelName; }

    public Status getStatus() { return status; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public Dataset getDataset() { return dataset; }

    public String getLogsPath() { return logsPath; }

    public String getMetricsPath() { return metricsPath; }

    public String getArtifactPath() { return artifactPath; }

    public String getErrorMessage() { return errorMessage; }

    public boolean isCancelRequested() { return cancelRequested; }

    public void setId(Long id) { this.id = id; }

    public void setModelName(String modelName) { this.modelName = modelName; }

    public void setStatus(Status status) { this.status = status; this.updatedAt = Instant.now(); }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public void setDataset(Dataset dataset) { this.dataset = dataset; }

    public void setLogsPath(String logsPath) { this.logsPath = logsPath; }

    public void setMetricsPath(String metricsPath) { this.metricsPath = metricsPath; }

    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public void setCancelRequested(boolean cancelRequested) { this.cancelRequested = cancelRequested; }
}
