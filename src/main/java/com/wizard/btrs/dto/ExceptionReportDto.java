package com.wizard.btrs.dto;

import com.wizard.btrs.enums.ReconciliationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionReportDto {

    private String txnId;

    private BigDecimal bankAmount;

    private BigDecimal gatewayAmount;

    private String bankStatus;

    private String gatewayStatus;

    private ReconciliationStatus reconciliationStatus;

    private String remarks;
}