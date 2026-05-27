package com.wizard.btrs.config;

import com.wizard.btrs.dto.GatewayTransactionRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final FlatFileItemReader<GatewayTransactionRecord>
            gatewayTransactionReader;

    private final ItemWriter<GatewayTransactionRecord>
            gatewayTransactionWriter;

    @Bean
    public Step gatewayLoadingStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {

        return new StepBuilder(
                "gatewayLoadingStep",
                jobRepository
        )
                .<GatewayTransactionRecord,
                        GatewayTransactionRecord>chunk(2)
                .transactionManager(transactionManager)
                .reader(gatewayTransactionReader)
                .writer(gatewayTransactionWriter)
                .build();
    }

    @Bean
    public Job reconciliationJob(
            JobRepository jobRepository,
            Step gatewayLoadingStep
    ) {

        return new JobBuilder(
                "reconciliationJob",
                jobRepository
        )
                .start(gatewayLoadingStep)
                .build();
    }
}