package com.expense.tracker.expense.external.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long transactionId;
    private Long userId;
    private Double amount;
    private LocalDateTime transactionDate;
    private Long categoryId;
    private Long paymentTypeId;
    private String description;
}
