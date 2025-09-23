package com.expense.tracker.core.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoanDTO {

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
}

