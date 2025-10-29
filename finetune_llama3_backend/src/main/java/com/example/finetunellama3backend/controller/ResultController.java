package com.example.finetunellama3backend.controller;

import com.example.finetunellama3backend.dto.ResultResponse;
import com.example.finetunellama3backend.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;

/**
 * Result API for listing results and downloading artifacts.
 */
@RestController
@RequestMapping("/api/results")
@Tag(name = "Results", description = "Retrieve finetuning results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List results", description = "Lists all finetune results")
    public List<ResultResponse> list() {
        return resultService.list();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}/artifact")
    @Operation(summary = "Download artifact", description = "Downloads the trained model artifact for a result")
    public ResponseEntity<Resource> artifact(@PathVariable Long id) {
        Resource resource = resultService.downloadArtifact(id);
        String file = Paths.get(resource.getFilename() != null ? resource.getFilename() : "artifact.bin").getFileName().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                .body(resource);
    }
}
