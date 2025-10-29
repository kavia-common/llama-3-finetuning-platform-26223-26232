package com.example.finetunellama3backend.repository;

import com.example.finetunellama3backend.model.FinetuneResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinetuneResultRepository extends JpaRepository<FinetuneResult, Long> {
}
