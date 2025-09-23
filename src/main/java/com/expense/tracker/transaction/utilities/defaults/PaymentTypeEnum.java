package com.expense.tracker.transaction.utilities.defaults;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // Map for fast lookup
    private static final Map<String, PaymentTypeEnum> NAME_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            paymentType -> paymentType.getName().toLowerCase(), // case-insensitive key
                            paymentType -> paymentType
                    ));


    // Optimized lookup by name
    public static PaymentTypeEnum getByName(String name) {
        if (name == null) return null;
        return NAME_MAP.get(name.toLowerCase());
    }
}
