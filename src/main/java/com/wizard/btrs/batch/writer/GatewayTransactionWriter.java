package com.wizard.btrs.batch.writer;

import com.wizard.btrs.dto.GatewayTransactionRecord;
import com.wizard.btrs.entity.GatewayTransaction;
import com.wizard.btrs.repository.GatewayTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatewayTransactionWriter
        implements ItemWriter<GatewayTransactionRecord> {

    private final GatewayTransactionRepository repository;

    @Override
    public void write(
            Chunk<? extends GatewayTransactionRecord> chunk
    ) {

        for (GatewayTransactionRecord item : chunk) {

            GatewayTransaction transaction =
                    GatewayTransaction.builder()
                            .txnId(item.getTxnId())
                            .amount(item.getAmount())
                            .status(item.getStatus())
                            .build();

            repository.save(transaction);

            System.out.println(
                    "Gateway Transaction Saved : "
                            + item.getTxnId()
            );
        }
    }
}