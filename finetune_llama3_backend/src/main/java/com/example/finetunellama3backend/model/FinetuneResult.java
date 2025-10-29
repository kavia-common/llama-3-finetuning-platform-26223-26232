package com.example.finetunellama3backend.model;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Result created after a job finishes successfully.
 */
@Entity
@Table(name = "finetune_results")
public class FinetuneResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelName;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private FinetuneJob job;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dataset dataset;

    private String artifactPath;
    private String metricsPath;

    private Instant createdAt = Instant.now();

    public FinetuneResult() {}

    public FinetuneResult(FinetuneJob job, Dataset dataset, String modelName, String artifactPath, String metricsPath) {
        this.job = job;
        this.dataset = dataset;
        this.modelName = modelName;
        this.artifactPath = artifactPath;
        this.metricsPath = metricsPath;
    }

    public Long getId() { return id; }

    public String getModelName() { return modelName; }

    public FinetuneJob getJob() { return job; }

    public Dataset getDataset() { return dataset; }

    public String getArtifactPath() { return artifactPath; }

    public String getMetricsPath() { return metricsPath; }

    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }

    public void setModelName(String modelName) { this.modelName = modelName; }

    public void setJob(FinetuneJob job) { this.job = job; }

    public void setDataset(Dataset dataset) { this.dataset = dataset; }

    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }

    public void setMetricsPath(String metricsPath) { this.metricsPath = metricsPath; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
