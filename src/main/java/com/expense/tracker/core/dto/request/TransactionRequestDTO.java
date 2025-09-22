package com.expense.tracker.core.dto.request;

import com.expense.tracker.expense.utilities.defaults.PaymentTypeEnum;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Payment type is required")
    private PaymentTypeEnum paymentType;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
