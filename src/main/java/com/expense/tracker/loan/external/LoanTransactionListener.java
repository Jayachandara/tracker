package com.expense.tracker.loan.external;

import com.expense.tracker.core.event.LoanTransactionEvent;
import com.expense.tracker.loan.internal.entity.LoanTransaction;
import com.expense.tracker.loan.internal.entity.UserLoan;
import com.expense.tracker.loan.internal.service.LoanTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoanTransactionListener {

    private final LoanTransactionService loanTransactionService;

    @Async
    @EventListener
    @Transactional
    public void handleTransactionEvent(LoanTransactionEvent event) {

         loanTransactionService.mapTransactionToLoan(
                event.getLoanId(), event.getTransactionId()
        );
    }
}

