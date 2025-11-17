package com.expense.tracker.transaction.utilities.defaults;

import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.transaction.internal.entity.Category;
import com.expense.tracker.transaction.internal.entity.Transaction;
import com.expense.tracker.transaction.internal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final CategoryRepository categoryRepository;

    public Transaction toEntity(TransactionDTO dto, Long userId) {
        Transaction tx = new Transaction();

        // set user
        tx.setUserId(userId);

        // amount
        tx.setAmount(dto.getAmount() != null ? dto.getAmount():0);

        // transactionDate: compose from date + time when available
//        if (dto.getDate() != null) {
//            if (dto.getTime() != null) {
//                tx.setTransactionDate(LocalDateTime.of(dto.getDate(), dto.getTime()));
//            } else {
//                tx.setTransactionDate(dto.getDate().atStartOfDay());
//            }
//        }

        // Also set separate date/time fields if your entity contains them
        try {
            var dateField = Transaction.class.getDeclaredField("transactionDate");
            dateField.setAccessible(true);
            if (dto.getTransactionDate() != null) dateField.set(tx, dto.getTransactionDate());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

//        try {
//            var timeField = Transaction.class.getDeclaredField("time");
//            timeField.setAccessible(true);
//            if (dto.getTime() != null) timeField.set(tx, dto.getTime());
//        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // DR/CR
        try {
            var f = Transaction.class.getDeclaredField("drCr");
            f.setAccessible(true);
            f.set(tx, dto.getDrCr());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // account
        try {
            var f = Transaction.class.getDeclaredField("account");
            f.setAccessible(true);
            f.set(tx, dto.getAccount());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // expense/income fields
        try {
            var f = Transaction.class.getDeclaredField("expense");
            f.setAccessible(true);
            f.set(tx, dto.getExpense());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        try {
            var f = Transaction.class.getDeclaredField("income");
            f.setAccessible(true);
            f.set(tx, dto.getIncome());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // category: try id first, otherwise by name
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepository.findById(dto.getCategoryId()).orElse(null);
            tx.setCategory(cat);
        }
//        else if (dto.getCategoryName() != null) {
//            Category cat = categoryRepository.findByNameIgnoreCase(dto.getCategoryName()).orElse(null);
//            tx.setCategory(cat);
//        }

        // tags -> entity field (List<String> via ElementCollection)
        try {
            var f = Transaction.class.getDeclaredField("tags");
            f.setAccessible(true);
            f.set(tx, dto.getTags() == null ? null : dto.getTags().stream().collect(Collectors.toList()));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // note/description
        tx.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getNote());

        // irregular & reimbursable & status
        try {
            var f = Transaction.class.getDeclaredField("isIrregularSpends");
            f.setAccessible(true);
            f.set(tx, dto.getIsIrregularSpends());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        try {
            var f = Transaction.class.getDeclaredField("isReimbursable");
            f.setAccessible(true);
            f.set(tx, dto.getIsReimbursable());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        try {
            var f = Transaction.class.getDeclaredField("reimbursementid");
            f.setAccessible(true);
            f.set(tx, dto.getReimbursementid());
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        return tx;
    }
}
