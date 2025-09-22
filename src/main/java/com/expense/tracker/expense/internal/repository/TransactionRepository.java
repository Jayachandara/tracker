package com.expense.tracker.expense.internal.repository;


import com.expense.tracker.expense.internal.entity.Transaction;
import com.expense.tracker.expense.utilities.defaults.PaymentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCategory_CategoryId(Long userId, Long categoryId);

    List<Transaction> findByUserIdAndPaymentType(Long userId, PaymentTypeEnum paymentTypeEnum);

}
