package com.wizard.btrs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReconciliationSummary {

    private long totalTransactions;
    private long matchedTransactions;
    private long statusMismatchTransactions;
    private long amountMismatchTransactions;
    private long missingInGatewayTransactions;
    private long extraInGatewayTransactions;
}