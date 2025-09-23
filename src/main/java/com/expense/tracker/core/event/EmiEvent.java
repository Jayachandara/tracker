package com.expense.tracker.core.event;

public record EmiEvent(Long loanId, Long userId, Double amount, String loanType, String loanName) {}

