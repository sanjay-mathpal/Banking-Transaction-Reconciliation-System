package com.wizard.btrs.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankTransactionRecord {

    private String txnId;

    private BigDecimal amount;

    private String status;
}