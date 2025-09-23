package com.expense.tracker.transaction.internal.repository;


import com.expense.tracker.transaction.internal.entity.Transaction;
import com.expense.tracker.transaction.utilities.defaults.PaymentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCategory_CategoryId(Long userId, Long categoryId);

    List<Transaction> findByUserIdAndPaymentType(Long userId, PaymentTypeEnum paymentTypeEnum);

    List<Transaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

}
