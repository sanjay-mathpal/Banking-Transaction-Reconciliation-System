package com.wizard.btrs.service;

import com.wizard.btrs.dto.ExceptionReportDto;
import com.wizard.btrs.dto.ReconciliationSummary;
import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.enums.ReconciliationStatus;
import com.wizard.btrs.repository.ReconciledTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReconciledTransactionRepository repository;

    public ReconciliationSummary getSummary() {

        long total = repository.count();

        long matched =
                repository.countByReconciliationStatus(
                        ReconciliationStatus.MATCHED);

        long statusMismatch =
                repository.countByReconciliationStatus(
                        ReconciliationStatus.STATUS_MISMATCH);

        long amountMismatch =
                repository.countByReconciliationStatus(
                        ReconciliationStatus.AMOUNT_MISMATCH);

        long missingInGateway =
                repository.countByReconciliationStatus(
                        ReconciliationStatus.MISSING_IN_GATEWAY);

        long extraInGateway =
                repository.countByReconciliationStatus(
                        ReconciliationStatus.EXTRA_IN_GATEWAY);

        return new ReconciliationSummary(
                total,
                matched,
                statusMismatch,
                amountMismatch,
                missingInGateway,
                extraInGateway
        );
    }

    private ExceptionReportDto mapToDto(
            ReconciledTransaction transaction
    ) {

        return ExceptionReportDto.builder()
                .txnId(transaction.getTxnId())
                .bankAmount(transaction.getBankAmount())
                .gatewayAmount(transaction.getGatewayAmount())
                .bankStatus(transaction.getBankStatus())
                .gatewayStatus(transaction.getGatewayStatus())
                .reconciliationStatus(
                        transaction.getReconciliationStatus()
                )
                .remarks(transaction.getRemarks())
                .build();
    }

    public List<ExceptionReportDto> getExceptionReport() {

        return repository
                .findByReconciliationStatusNot(
                        ReconciliationStatus.MATCHED
                )
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}