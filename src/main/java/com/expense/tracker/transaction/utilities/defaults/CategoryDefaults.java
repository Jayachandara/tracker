package com.expense.tracker.transaction.utilities.defaults;

import java.util.List;

public class CategoryDefaults {

    public static List<CategorySeed> getDefaults() {
        return List.of(
                new CategorySeed("Home Rent", CategoryTypeEnum.HOME, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Home Groceries", CategoryTypeEnum.HOME, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Electricity", CategoryTypeEnum.UTILITIES, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Water", CategoryTypeEnum.UTILITIES, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Internet", CategoryTypeEnum.UTILITIES, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Housekeeping", CategoryTypeEnum.SERVICES, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Repairs", CategoryTypeEnum.SERVICES, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Restaurant", CategoryTypeEnum.FOOD, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Snacks", CategoryTypeEnum.FOOD, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Fuel", CategoryTypeEnum.TRANSPORT, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Public Transport", CategoryTypeEnum.TRANSPORT, CategoryGroupEnum.EXPENSES),
                new CategorySeed("Salary", CategoryTypeEnum.JOB, CategoryGroupEnum.INCOME),
                new CategorySeed("Bonus", CategoryTypeEnum.JOB, CategoryGroupEnum.INCOME),
                new CategorySeed("Stocks", CategoryTypeEnum.EQUITY, CategoryGroupEnum.INVESTMENT),
                new CategorySeed("Mutual Funds", CategoryTypeEnum.EQUITY, CategoryGroupEnum.INVESTMENT),
                new CategorySeed("Bank Savings", CategoryTypeEnum.BANK, CategoryGroupEnum.SAVINGS),
                new CategorySeed("Fixed Deposit", CategoryTypeEnum.BANK, CategoryGroupEnum.SAVINGS),
                new CategorySeed("Personal Loan 1", CategoryTypeEnum.PERSONAL_LOAN, CategoryGroupEnum.LOANS)
                );
    }

    public record CategorySeed(String name, CategoryTypeEnum type, CategoryGroupEnum group) {}
}