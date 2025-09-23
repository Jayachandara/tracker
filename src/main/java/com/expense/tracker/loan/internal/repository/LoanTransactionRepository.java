package com.expense.tracker.loan.internal.repository;

import com.expense.tracker.loan.internal.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Long> {
    boolean existsByLoan_LoanIdAndTransaction_TransactionDate(Long loanId, LocalDateTime transactionDate);
    boolean existsByLoan_LoanIdAndTransaction_TransactionId(Long loanId, Long transactionId);
}

