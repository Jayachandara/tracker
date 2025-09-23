package com.expense.tracker.core.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanTransactionEvent {
    private Long loanId;
    private Long transactionId;
}

