package com.expense.tracker.transaction.utilities.defaults;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CategoryTypeEnum {
    // INCOME
    JOB("Job", CategoryGroupEnum.INCOME),
    BUSINESS("Business", CategoryGroupEnum.INCOME),
    OTHER_INCOME("Other Income", CategoryGroupEnum.INCOME),

    // SAVINGS
    SAVINGS_ACCOUNT("Savings Account", CategoryGroupEnum.SAVINGS),
    FIXED_DEPOSIT("Fixed Deposit", CategoryGroupEnum.SAVINGS),
    RECURRING_DEPOSIT("Recurring Deposit", CategoryGroupEnum.SAVINGS),
    OTHER_SAVINGS("Other Saving", CategoryGroupEnum.SAVINGS),

    // INVESTMENT
    MUTUAL_FUNDS("Mutual Funds", CategoryGroupEnum.INVESTMENT),
    STOCKS("Stocks", CategoryGroupEnum.INVESTMENT),
    LAND_INVESTMENT("Land Investment", CategoryGroupEnum.INVESTMENT),
    HOME_INVESTMENT("Home Investment", CategoryGroupEnum.INVESTMENT),
    OTHER_INVESTMENT("Other Investment", CategoryGroupEnum.INVESTMENT),

    // EXPENSES
    HOUSING("Housing", CategoryGroupEnum.EXPENSES),
    UTILITIES("Utilities", CategoryGroupEnum.EXPENSES),
    SERVICES("Services", CategoryGroupEnum.EXPENSES),
    ENTERTAINMENT("Entertainment", CategoryGroupEnum.EXPENSES),
    FOOD("Food", CategoryGroupEnum.EXPENSES),
    TRANSPORT("Transport", CategoryGroupEnum.EXPENSES),
    HEALTH("Health", CategoryGroupEnum.EXPENSES),
    EDUCATION("Education", CategoryGroupEnum.EXPENSES),
    OTHER_EXPENSES("Other Expense", CategoryGroupEnum.EXPENSES),

    // LOANS
    PERSONAL_LOAN("Personal Loan", CategoryGroupEnum.LOANS),
    HOME_LOAN("Home Loan", CategoryGroupEnum.LOANS),
    CAR_LOAN("Car Loan", CategoryGroupEnum.LOANS),
    BIKE_LOAN("Bike Loan", CategoryGroupEnum.LOANS),
    OTHER_LOAN("Other Loan", CategoryGroupEnum.LOANS);

    private final String name;
    private final CategoryGroupEnum group;

    CategoryTypeEnum(String name, CategoryGroupEnum group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public CategoryGroupEnum getGroup() {
        return group;
    }

    // Map for fast lookup
    private static final Map<String, CategoryTypeEnum> NAME_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            categoryType -> categoryType.getName().toLowerCase(), // case-insensitive key
                            categoryType -> categoryType
                    ));


    // Optimized lookup by name
    public static CategoryTypeEnum getByName(String name) {
        if (name == null) return null;
        return NAME_MAP.get(name.toLowerCase());
    }

}