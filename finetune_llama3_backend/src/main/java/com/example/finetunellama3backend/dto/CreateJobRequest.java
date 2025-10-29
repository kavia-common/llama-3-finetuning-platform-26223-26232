package com.example.finetunellama3backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request body to create a finetune job.
 */
public class CreateJobRequest {
    @Schema(description = "Dataset ID to finetune on", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long datasetId;

    @Schema(description = "Base model name to finetune", example = "llama3-8b", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelName;

    public Long getDatasetId() { return datasetId; }

    public String getModelName() { return modelName; }

    public void setDatasetId(Long datasetId) { this.datasetId = datasetId; }

    public void setModelName(String modelName) { this.modelName = modelName; }
}
