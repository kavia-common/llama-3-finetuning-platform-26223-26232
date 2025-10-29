package com.example.finetunellama3backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for storage.
 * Root path can be set via app.storage.root or defaults to ./storage.
 */
@Configuration
@ConfigurationProperties(prefix = "app.storage")
public class StorageConfig {

    /**
     * Root directory for storing datasets, logs, and artifacts.
     */
    private String root = "./storage";

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
