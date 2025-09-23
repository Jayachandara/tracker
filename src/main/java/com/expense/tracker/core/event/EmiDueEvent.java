package com.expense.tracker.core.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmiDueEvent(Long loanId, Long userId, Double amount, LocalDate dueDate) {}

