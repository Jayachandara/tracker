package com.expense.tracker.transaction.internal.service;

import com.expense.tracker.transaction.internal.entity.Transaction;
import com.expense.tracker.transaction.internal.repository.TransactionRepository;
import com.expense.tracker.transaction.utilities.defaults.PaymentTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public List<Transaction> getAllByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Transaction> getByUserAndDateRange(Long userId, LocalDateTime start, LocalDateTime end) {
        return repository.findByUserIdAndTransactionDateBetween(userId, start, end);
    }

    public Optional<Transaction> getById(Long id) {
        return repository.findById(id);
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public Transaction update(Long userId, Long id, Transaction updated) {
        return repository.findById(id)
                .filter(tx -> tx.getUserId().equals(userId))
                .map(existing -> {
                    // core fields
                    existing.setTransactionId(updated.getTransactionId());
                    existing.setAmount(updated.getAmount());
                    existing.setTransactionDate(updated.getTransactionDate());
                    existing.setCategory(updated.getCategory());
                    existing.setPaymentType(updated.getPaymentType());
                    existing.setDescription(updated.getDescription());


                    // DR/CR and account
                    existing.setDrCr(updated.getDrCr());
                    existing.setAccount(updated.getAccount());

                    // expense / income fields
                    existing.setExpense(updated.getExpense());
                    existing.setIncome(updated.getIncome());

                    // tags, note
                    existing.setTags(updated.getTags());
                    existing.setNote(updated.getNote());

                    // irregular and reimbursement
                    existing.setIsIrregularSpends(updated.getIsIrregularSpends());
                    existing.setIsReimbursable(updated.getIsReimbursable());
                    existing.setReimbursementid(updated.getReimbursementid());

                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }

    public List<Transaction> getByCategory(Long userId, Long categoryId) {
        return repository.findByUserIdAndCategory_CategoryId(userId, categoryId);
    }

    public List<Transaction> getByPaymentType(Long userId, PaymentTypeEnum paymentTypeEnum) {
        return repository.findByUserIdAndPaymentType(userId, paymentTypeEnum);
    }
}
