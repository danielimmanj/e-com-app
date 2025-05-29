package com.ecom.inventory.common.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
public class StopWatchJobListener implements JobExecutionListener {

    private final StopWatch stopWatch = new StopWatch("SpringBatchJob");

    @Override
    public void beforeJob(JobExecution jobExecution) {
        stopWatch.start(jobExecution.getJobInstance().getJobName());
        log.info("Job {} started at {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        stopWatch.stop();
        log.info("Job {} ended at {}", jobExecution.getJobInstance().getJobName(), jobExecution.getEndTime());
        log.info("Total time taken by job {}: {} ms", jobExecution.getJobInstance().getJobName(), stopWatch.getTotalTimeMillis());
        log.info("Pretty Print:\n{}", stopWatch.prettyPrint());
    }
}