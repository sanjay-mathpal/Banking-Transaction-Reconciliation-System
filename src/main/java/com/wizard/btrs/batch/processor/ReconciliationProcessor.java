package com.wizard.btrs.batch.processor;

import com.wizard.btrs.dto.BankTransactionRecord;
import com.wizard.btrs.entity.GatewayTransaction;
import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.enums.ReconciliationStatus;
import com.wizard.btrs.repository.GatewayTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReconciliationProcessor
        implements
        ItemProcessor<
                BankTransactionRecord,
                ReconciledTransaction> {

    private final GatewayTransactionRepository
            gatewayRepository;

    @Override
    public ReconciledTransaction process(
            BankTransactionRecord bankTransaction
    ) {

        Optional<GatewayTransaction> optionalGatewayTxn =
                gatewayRepository.findByTxnId(
                        bankTransaction.getTxnId()
                );

        if (optionalGatewayTxn.isEmpty()) {

            return ReconciledTransaction.builder()
                    .txnId(bankTransaction.getTxnId())
                    .bankAmount(bankTransaction.getAmount())
                    .bankStatus(bankTransaction.getStatus())
                    .reconciliationStatus(
                            ReconciliationStatus.MISSING_IN_GATEWAY
                    )
                    .remarks("Transaction missing in gateway")
                    .build();
        }

        GatewayTransaction gatewayTransaction =
                optionalGatewayTxn.get();

        if (bankTransaction.getAmount().compareTo(
                gatewayTransaction.getAmount()) != 0) {

            return ReconciledTransaction.builder()
                    .txnId(bankTransaction.getTxnId())
                    .bankAmount(bankTransaction.getAmount())
                    .gatewayAmount(gatewayTransaction.getAmount())
                    .bankStatus(bankTransaction.getStatus())
                    .gatewayStatus(gatewayTransaction.getStatus())
                    .reconciliationStatus(
                            ReconciliationStatus.AMOUNT_MISMATCH
                    )
                    .remarks("Amount mismatch")
                    .build();
        }

        if (!bankTransaction.getStatus().equalsIgnoreCase(
                gatewayTransaction.getStatus())) {

            return ReconciledTransaction.builder()
                    .txnId(bankTransaction.getTxnId())
                    .bankAmount(bankTransaction.getAmount())
                    .gatewayAmount(gatewayTransaction.getAmount())
                    .bankStatus(bankTransaction.getStatus())
                    .gatewayStatus(gatewayTransaction.getStatus())
                    .reconciliationStatus(
                            ReconciliationStatus.STATUS_MISMATCH
                    )
                    .remarks("Status mismatch")
                    .build();
        }

        return ReconciledTransaction.builder()
                .txnId(bankTransaction.getTxnId())
                .bankAmount(bankTransaction.getAmount())
                .gatewayAmount(gatewayTransaction.getAmount())
                .bankStatus(bankTransaction.getStatus())
                .gatewayStatus(gatewayTransaction.getStatus())
                .reconciliationStatus(
                        ReconciliationStatus.MATCHED
                )
                .remarks("Transaction matched")
                .build();
    }
}