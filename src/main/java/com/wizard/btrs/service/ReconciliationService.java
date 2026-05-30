package com.wizard.btrs.service;

import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.repository.ReconciledTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReconciliationService {

    private final ReconciledTransactionRepository repository;

    public List<ReconciledTransaction> getAllResults() {

        return repository.findAll();
    }
}