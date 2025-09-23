package com.expense.tracker.transaction.utilities.defaults;

public enum PaymentTypeEnum {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    UPI("UPI"),
    BANK_TRANSFER("Bank Transfer"),
    CHEQUE("Cheque");

    private final String name;

    PaymentTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
