package com.expense.tracker.expense.internal.service;

import com.expense.tracker.expense.internal.entity.Expense;
import com.expense.tracker.expense.internal.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense createExpense(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> getExpensesByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }
}
