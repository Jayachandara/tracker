package com.expense.tracker.expense.utilities.defaults;

public enum CategoryTypeEnum {
    // INCOME
    JOB("Job", CategoryGroupEnum.INCOME),
    BUSINESS("Business", CategoryGroupEnum.INCOME),
    OTHER_INCOME("Other Income", CategoryGroupEnum.INCOME),

    // SAVINGS
    SAVINGS_ACCOUNT("Savings Account", CategoryGroupEnum.SAVINGS),
    FIXED_DEPOSIT("Fixed Deposit", CategoryGroupEnum.SAVINGS),
    RECURRING_DEPOSIT("Recurring Deposit", CategoryGroupEnum.SAVINGS),
    OTHER_SAVINGS("Other Savings", CategoryGroupEnum.SAVINGS),

    // INVESTMENT
    EQUITY("Equity", CategoryGroupEnum.INVESTMENT),
    BANK("Bank Investments", CategoryGroupEnum.INVESTMENT),
    OTHER_INVESTMENT("Other Investments", CategoryGroupEnum.INVESTMENT),

    // EXPENSES
    HOME("Home", CategoryGroupEnum.EXPENSES),
    UTILITIES("Utilities", CategoryGroupEnum.EXPENSES),
    SERVICES("Services", CategoryGroupEnum.EXPENSES),
    ENTERTAINMENT("Entertainment", CategoryGroupEnum.EXPENSES),
    FOOD("Food", CategoryGroupEnum.EXPENSES),
    TRANSPORT("Transport", CategoryGroupEnum.EXPENSES),
    HEALTH("Health", CategoryGroupEnum.EXPENSES),
    EDUCATION("Education", CategoryGroupEnum.EXPENSES),
    OTHER_EXPENSES("Other Expenses", CategoryGroupEnum.EXPENSES),

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
}