package com.example.finetunellama3backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for the Llama 3 Finetuning Backend.
 * Enables async execution and scheduling used by the job executor.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@OpenAPIDefinition(info = @Info(
        title = "Llama 3 Finetuning Backend",
        version = "0.1.0",
        description = "APIs for dataset upload, job scheduling/execution (simulated), and results retrieval."
))
public class FinetuneLlama3BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinetuneLlama3BackendApplication.class, args);
    }
}
