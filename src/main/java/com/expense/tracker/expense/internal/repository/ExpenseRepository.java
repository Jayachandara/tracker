package com.expense.tracker.expense.internal.repository;

import com.expense.tracker.expense.internal.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserId(Integer userId);
}
