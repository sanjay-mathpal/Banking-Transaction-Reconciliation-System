package com.wizard.btrs.repository;

import com.wizard.btrs.entity.GatewayTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GatewayTransactionRepository
        extends JpaRepository<GatewayTransaction, Long> {

    Optional<GatewayTransaction> findByTxnId(String txnId);
}