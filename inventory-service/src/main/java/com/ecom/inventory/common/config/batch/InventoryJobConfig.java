package com.ecom.inventory.common.config.batch;

import com.ecom.inventory.business.dto.InventoryDto;
import com.ecom.inventory.business.model.Inventory;
import jakarta.annotation.Nullable;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class InventoryJobConfig {

    private final FlatFileItemReader<InventoryDto> inventoryReader;
    private final ItemProcessor<InventoryDto, Inventory> inventoryProcessor;
    private final JpaItemWriter<Inventory> inventoryWriter;

    @Bean
    public Step inventoryStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("inventoryStep", jobRepository)
                .<InventoryDto, Inventory>chunk(1000, transactionManager)
                .reader(inventoryReader)
                .processor(inventoryProcessor)
                .writer(inventoryWriter)
                .faultTolerant()
                // Skip logic
//                .skip(IllegalArgumentException.class) // specific exception skip
//                .skipLimit(100) // up to 100 items can be skipped before failing the job
                .skipPolicy(new CustomInventorySkipPolicy())  // Custom skip policy to handle more condition
                .listener(skipListener())
                // Retry logic
                .retry(RuntimeException.class)
                .retryLimit(3) // retry 3 times before failing the chunk
                .build();
    }

    @Bean
    public Job inventoryJob(JobRepository jobRepository, Step inventoryStep, StopWatchJobListener stopWatchJobListener) {
        return new JobBuilder("inventoryJob", jobRepository)
                .start(inventoryStep)
                .listener(stopWatchJobListener)
                .build();
    }

    @Bean
    public SkipListener<InventoryDto, Inventory> skipListener() {
        return new SkipListener<>() {
            @Override
            public void onSkipInProcess(@Nullable InventoryDto item, @Nullable Throwable t) {
                System.out.println("⚠️ Skipped writing item: " + item + " due to: " + (Objects.nonNull(t) ? t.getMessage() : "Unknown error"));
            }
        };
    }
}
