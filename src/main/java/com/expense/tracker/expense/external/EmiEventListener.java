package com.expense.tracker.expense.external;

import com.expense.tracker.core.event.EmiEvent;
import com.expense.tracker.core.event.LoanTransactionEvent;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.Transaction;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.internal.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EmiEventListener {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final ApplicationEventPublisher publisher;

    @Async
    @EventListener
    @Transactional
    public void handleEmiEvent(EmiEvent event) {

        Category category = categoryService.getByUserAndName(event.userId(), event.loanName());

      /*  boolean exists = transactionService.existsByUserIdAndTransactionDateAndAmount(
                event.userId(), LocalDateTime.now(), event.amount()
        );
        if (exists) return;*/

        Transaction txn = Transaction.builder()
                .userId(event.userId())
                .amount(event.amount())
                .transactionDate(LocalDateTime.now())
                .category(category)
                .description("EMI for " + event.loanName() + " (" + event.loanType() + ")")
                .build();

        txn = transactionService.create(txn);

        // Publish LoanTransactionEvent back to Loan module
        publisher.publishEvent(LoanTransactionEvent.builder()
                .loanId(event.loanId())
                .transactionId(txn.getTransactionId())
                .build());
    }
}
