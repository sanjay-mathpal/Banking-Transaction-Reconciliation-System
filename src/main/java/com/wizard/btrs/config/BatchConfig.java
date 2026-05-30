package com.wizard.btrs.config;

import com.wizard.btrs.batch.tasklet.CleanupTasklet;
import com.wizard.btrs.batch.tasklet.ExtraGatewayCheckTasklet;
import com.wizard.btrs.dto.BankTransactionRecord;
import com.wizard.btrs.dto.GatewayTransactionRecord;
import com.wizard.btrs.entity.ReconciledTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
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
    private final FlatFileItemReader<BankTransactionRecord>
            bankTransactionReader;

    private final ItemProcessor<
                BankTransactionRecord,
                ReconciledTransaction> reconciliationProcessor;
    private final CleanupTasklet cleanupTasklet;

    private final ItemWriter<ReconciledTransaction>
            reconciliationWriter;

    @Bean
    public Step cleanupStep( JobRepository jobRepository,
                             PlatformTransactionManager transactionManager) {

        return new StepBuilder(
                "cleanupStep",
                jobRepository
        )
                .tasklet(
                        cleanupTasklet,
                        transactionManager
                )
                .build();
    }

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
    public Step reconciliationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {

        return new StepBuilder(
                "reconciliationStep",
                jobRepository
        )
                .<BankTransactionRecord,
                        ReconciledTransaction>chunk(2)
                .transactionManager(transactionManager)
                .reader(bankTransactionReader)
                .processor(reconciliationProcessor)
                .writer(reconciliationWriter)
                .build();
    }

    @Bean
    public Step extraGatewayCheckStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ExtraGatewayCheckTasklet tasklet) {

        return new StepBuilder(
                "extraGatewayCheckStep",
                jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Job reconciliationJob(
            JobRepository jobRepository,
            Step cleanupStep,
            Step gatewayLoadingStep,
            Step reconciliationStep,
            Step extraGatewayCheckStep
    ) {

        return new JobBuilder(
                "reconciliationJob",
                jobRepository
        )
                .start(cleanupStep)
                .next(gatewayLoadingStep)
                .next(reconciliationStep)
                .next(extraGatewayCheckStep)
                .build();
    }
}