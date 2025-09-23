package com.expense.tracker.core.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoanRequestDTO {

    private Long userId;
    private String loanType;
    private String loanName;
    private String lenderName;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private LocalDate startDate;
}

