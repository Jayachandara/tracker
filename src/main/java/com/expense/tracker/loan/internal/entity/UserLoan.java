package com.expense.tracker.loan.internal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private Long userId;

    private String loanType;

    private String loanName;

    private String lenderName;

    private Double loanAmount;

    private Double interestRate;

    private Integer tenureMonths;

    private Double emiAmount;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum LoanStatus {
        ACTIVE,
        CLOSED,
        DEFAULTED
    }
}

