package com.wizard.btrs.repository;

import com.wizard.btrs.entity.ReconciledTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciledTransactionRepository
        extends JpaRepository<ReconciledTransaction, Long> {
}