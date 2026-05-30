package com.wizard.btrs.batch.tasklet;

import com.wizard.btrs.repository.GatewayTransactionRepository;
import com.wizard.btrs.repository.ReconciledTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanupTasklet implements Tasklet {

    private final GatewayTransactionRepository gatewayRepository;
    private final ReconciledTransactionRepository reconciledRepository;

    @Override
    public RepeatStatus execute(
            @NonNull StepContribution contribution,
            @NonNull ChunkContext chunkContext) {

        reconciledRepository.deleteAll();
        gatewayRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
