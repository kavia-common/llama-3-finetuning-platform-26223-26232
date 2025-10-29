package com.example.finetunellama3backend.repository;

import com.example.finetunellama3backend.model.FinetuneJob;
import com.example.finetunellama3backend.model.FinetuneJob.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinetuneJobRepository extends JpaRepository<FinetuneJob, Long> {
    List<FinetuneJob> findByStatus(Status status);
}
