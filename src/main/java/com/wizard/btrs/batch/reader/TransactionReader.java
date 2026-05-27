package com.wizard.btrs.batch.reader;

import com.wizard.btrs.dto.BankTransactionRecord;
import com.wizard.btrs.dto.GatewayTransactionRecord;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class TransactionReader {

    @Bean
    public FlatFileItemReader<GatewayTransactionRecord>
    gatewayTransactionReader() {

        return new FlatFileItemReaderBuilder<GatewayTransactionRecord>()
                .name("gatewayTransactionReader")
                .resource(new ClassPathResource(
                        "csv/gateway_transactions.csv"
                ))
                .delimited()
                .names("txnId", "amount", "status")
                .targetType(GatewayTransactionRecord.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public FlatFileItemReader<BankTransactionRecord>
    bankTransactionReader() {

        return new FlatFileItemReaderBuilder<BankTransactionRecord>()
                .name("bankTransactionReader")
                .resource(new ClassPathResource(
                        "csv/bank_transactions.csv"
                ))
                .delimited()
                .names("txnId", "amount", "status")
                .targetType(BankTransactionRecord.class)
                .linesToSkip(1)
                .build();
    }
}