package com.example.StudyDemo.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductBatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(ProductBatchScheduler.class);

    private final JobLauncher jobLauncher;
    private final Job productJob;

    public ProductBatchScheduler(
            JobLauncher jobLauncher,
            Job productJob) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
    }

    // 每半分鐘跑一次
    // @Scheduled(fixedRate = 30000)
    // ⏰ 每天凌晨 2 點
    @Scheduled(cron = "0 0 2 * * ?")
    public void runBatch() {

        try {

            log.info("=== Batch START ===");

            jobLauncher.run(
                    productJob,
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters());

            log.info("=== Batch END ===");

        } catch (Exception e) {

            log.error("Batch ERROR", e);
        }
    }
}