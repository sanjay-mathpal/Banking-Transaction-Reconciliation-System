package com.wizard.btrs.batch.tasklet;

import com.wizard.btrs.entity.GatewayTransaction;
import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.enums.ReconciliationStatus;
import com.wizard.btrs.repository.GatewayTransactionRepository;
import com.wizard.btrs.repository.ReconciledTransactionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;

import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExtraGatewayCheckTasklet implements Tasklet {

    private final GatewayTransactionRepository gatewayRepository;
    private final ReconciledTransactionRepository reconciledRepository;

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext) {

        List<GatewayTransaction> gatewayTransactions =
                gatewayRepository.findAll();

        for (GatewayTransaction gatewayTxn : gatewayTransactions) {

            boolean exists =
                    reconciledRepository.existsByTxnId(
                            gatewayTxn.getTxnId());

            if (!exists) {

                ReconciledTransaction transaction =
                        ReconciledTransaction.builder()
                                .txnId(gatewayTxn.getTxnId())
                                .gatewayAmount(gatewayTxn.getAmount())
                                .gatewayStatus(gatewayTxn.getStatus())
                                .reconciliationStatus(
                                        ReconciliationStatus.EXTRA_IN_GATEWAY
                                )
                                .remarks(
                                        "Transaction exists only in gateway"
                                )
                                .build();

                reconciledRepository.save(transaction);
            }
        }

        return RepeatStatus.FINISHED;
    }
}