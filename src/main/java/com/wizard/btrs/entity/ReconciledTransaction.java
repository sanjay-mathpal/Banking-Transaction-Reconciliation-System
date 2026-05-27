package com.wizard.btrs.entity;

import com.wizard.btrs.enums.ReconciliationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "reconciled_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciledTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txnId;

    private BigDecimal bankAmount;

    private BigDecimal gatewayAmount;

    private String bankStatus;

    private String gatewayStatus;

    @Enumerated(EnumType.STRING)
    private ReconciliationStatus reconciliationStatus;

    private String remarks;
}