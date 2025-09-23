package com.expense.tracker.loan.internal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Immutable
@Entity
@Table(name = "loan_payment_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPaymentSummary {

    @Id
    private Long loanId;

    private Long userId;

    private String loanType;

    private String loanName;

    private String lenderName;

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    private Integer tenureMonths;

    private BigDecimal emiAmount;

    private LocalDate startDate;

    private String status;

    private Integer monthsPaid;

    private BigDecimal totalPaid;

    private Integer remainingMonths;

    private BigDecimal remainingBalance;

    private LocalDate nextDueDate;
}

