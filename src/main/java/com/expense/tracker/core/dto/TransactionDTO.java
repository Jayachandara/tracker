package com.expense.tracker.core.dto;

import com.expense.tracker.expense.utilities.defaults.PaymentTypeEnum;
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
    private PaymentTypeEnum paymentType;
    private String description;
}
