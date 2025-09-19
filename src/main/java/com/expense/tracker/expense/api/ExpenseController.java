package com.expense.tracker.expense.api;


import com.expense.tracker.expense.internal.entity.Expense;
import com.expense.tracker.expense.internal.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return service.createExpense(expense);
    }

    @GetMapping("/user/{userId}")
    public List<Expense> getExpenses(@PathVariable Integer userId) {
        return service.getExpensesByUserId(userId);
    }
}
