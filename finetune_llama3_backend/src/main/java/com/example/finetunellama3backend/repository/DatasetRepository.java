package com.example.finetunellama3backend.repository;

import com.example.finetunellama3backend.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
}
