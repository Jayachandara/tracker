package com.expense.tracker.loan.internal.service;

import com.expense.tracker.loan.internal.entity.LoanTransaction;
import com.expense.tracker.loan.internal.entity.UserLoan;
import com.expense.tracker.loan.internal.repository.LoanTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanTransactionService {

    private final LoanTransactionRepository loanTransactionRepository;

    /**
     * Map a Transaction to a LoanTransaction for the given UserLoan
     * Checks idempotency to prevent duplicates
     */
    @Transactional
    public LoanTransaction mapTransactionToLoan(Long loanId, Long transactionId) {

        boolean exists = loanTransactionRepository.existsByLoan_LoanIdAndTransaction_TransactionId(
                loanId,
                transactionId
        );

        if (exists) {
            return loanTransactionRepository.findById(transactionId)
                    .orElseThrow(() -> new RuntimeException("Existing LoanTransaction not found"));
        }

        LoanTransaction loanTxn = LoanTransaction.builder()
                .loanId(loanId)
                .transactionId(transactionId)
                .createdAt(LocalDateTime.now())
                .build();

        return loanTransactionRepository.save(loanTxn);
    }
}

