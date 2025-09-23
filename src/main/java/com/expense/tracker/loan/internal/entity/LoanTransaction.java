package com.expense.tracker.loan.internal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanTransactionId;

    @Column(name = "loan_id", nullable = false)
    private Long loanId;

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    private LocalDateTime createdAt;
}

