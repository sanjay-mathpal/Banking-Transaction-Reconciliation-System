package com.wizard.btrs.repository;

import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.enums.ReconciliationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciledTransactionRepository
        extends JpaRepository<ReconciledTransaction, Long> {

    long countByReconciliationStatus(
            ReconciliationStatus reconciliationStatus
    );

    boolean existsByTxnId(String txnId);
}