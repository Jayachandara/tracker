package com.expense.tracker.core.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long transactionId;
    private LocalDateTime transactionDate;
    private Double amount;
    private String drCr;
    private String account;
    private String expense;
    private String income;
    private Long categoryId;
    private String categoryName;
    private List<String> tags;
    private String note;
    private Boolean isIrregularSpends;
    private Boolean isReimbursable;
    private Long reimbursementid;
    private String description;
    private Long userId;
}
