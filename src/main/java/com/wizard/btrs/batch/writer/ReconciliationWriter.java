package com.wizard.btrs.batch.writer;

import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.repository.ReconciledTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReconciliationWriter
        implements ItemWriter<ReconciledTransaction> {

    private final ReconciledTransactionRepository
            repository;

    @Override
    public void write(
            Chunk<? extends ReconciledTransaction> chunk
    ) {

        repository.saveAll(chunk.getItems());

        System.out.println(
                "Reconciled Transactions Saved : "
                        + chunk.size()
        );
    }
}