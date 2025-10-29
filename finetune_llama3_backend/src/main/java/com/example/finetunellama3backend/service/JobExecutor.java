package com.example.finetunellama3backend.service;

import com.example.finetunellama3backend.model.FinetuneJob;
import com.example.finetunellama3backend.model.FinetuneResult;
import com.example.finetunellama3backend.repository.FinetuneJobRepository;
import com.example.finetunellama3backend.repository.FinetuneResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Periodically scans for queued jobs and executes them asynchronously.
 * Simulates training by writing logs, metrics, and a fake artifact.
 */
@Component
public class JobExecutor {

    private static final Logger log = LoggerFactory.getLogger(JobExecutor.class);

    private final FinetuneJobRepository jobRepository;
    private final FinetuneResultRepository resultRepository;
    private final StorageService storageService;
    private final Executor executor;

    public JobExecutor(FinetuneJobRepository jobRepository,
                       FinetuneResultRepository resultRepository,
                       StorageService storageService) {
        this.jobRepository = jobRepository;
        this.resultRepository = resultRepository;
        this.storageService = storageService;

        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(2);
        t.setMaxPoolSize(4);
        t.setThreadNamePrefix("job-exec-");
        t.initialize();
        this.executor = t;
    }

    @Scheduled(fixedDelay = 2000L, initialDelay = 2000L)
    public void pollQueue() {
        List<FinetuneJob> queued = jobRepository.findByStatus(FinetuneJob.Status.QUEUED);
        for (FinetuneJob job : queued) {
            job.setStatus(FinetuneJob.Status.RUNNING);
            jobRepository.save(job);
            executor.execute(() -> runJob(job.getId()));
        }
    }

    private void runJob(Long jobId) {
        AtomicBoolean failed = new AtomicBoolean(false);
        FinetuneJob job = jobRepository.findById(jobId).orElse(null);
        if (job == null) return;

        try {
            simulateTraining(job);
        } catch (Exception e) {
            failed.set(true);
            log.error("Job {} failed", jobId, e);
            job.setStatus(FinetuneJob.Status.FAILED);
            job.setErrorMessage(e.getMessage());
            jobRepository.save(job);
            return;
        }

        if (!failed.get() && job.getStatus() != FinetuneJob.Status.CANCELED) {
            job.setStatus(FinetuneJob.Status.SUCCEEDED);
            jobRepository.save(job);

            // Persist result entry
            FinetuneResult result = new FinetuneResult(job, job.getDataset(), job.getModelName(), job.getArtifactPath(), job.getMetricsPath());
            resultRepository.save(result);
        }
    }

    private void simulateTraining(FinetuneJob job) throws IOException, InterruptedException {
        String model = job.getModelName();
        Long id = job.getId();

        storageService.writeLog(id, "Starting training for job " + id + " on model " + model + "\n", false);

        for (int epoch = 1; epoch <= 5; epoch++) {
            if (checkCancel(job.getId())) {
                job = jobRepository.findById(job.getId()).orElseThrow();
                job.setStatus(FinetuneJob.Status.CANCELED);
                jobRepository.save(job);
                storageService.writeLog(id, "Training canceled at epoch " + epoch + "\n", true);
                return;
            }

            Thread.sleep(500); // simulate work
            double loss = Math.max(0.1, 2.0 / epoch);
            storageService.writeLog(id, "Epoch " + epoch + " - loss: " + String.format("%.4f", loss) + "\n", true);
            job.setLogsPath(storageService.prepareJobDir(id).resolve("logs.txt").toString());
            jobRepository.save(job);
        }

        // write metrics
        String metricsJson = "{\"final_loss\":0.1234,\"accuracy\":0.9876}";
        job.setMetricsPath(storageService.writeMetrics(id, metricsJson).toString());

        // write artifact
        job.setArtifactPath(storageService.writeArtifact(id, model).toString());

        jobRepository.save(job);
        storageService.writeLog(id, "Training completed successfully.\n", true);
    }

    private boolean checkCancel(Long jobId) {
        return jobRepository.findById(jobId).map(FinetuneJob::isCancelRequested).orElse(false);
    }
}
