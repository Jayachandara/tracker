package com.expense.tracker.transaction.utilities.defaults;

import java.util.List;

public class CategoryDefaults {

    public static List<CategorySeed> getDefaults() {
        return List.of(
                new CategorySeed("Home Rent", CategoryTypeEnum.HOUSING.getName()),
                new CategorySeed("Home Groceries", CategoryTypeEnum.HOUSING.getName()),
                new CategorySeed("Electricity", CategoryTypeEnum.UTILITIES.getName()),
                new CategorySeed("Water", CategoryTypeEnum.UTILITIES.getName()),
                new CategorySeed("Internet", CategoryTypeEnum.UTILITIES.getName()),
                new CategorySeed("Housekeeping", CategoryTypeEnum.SERVICES.getName()),
                new CategorySeed("Repairs", CategoryTypeEnum.SERVICES.getName()),
                new CategorySeed("Restaurant", CategoryTypeEnum.FOOD.getName()),
                new CategorySeed("Snacks", CategoryTypeEnum.FOOD.getName()),
                new CategorySeed("Fuel", CategoryTypeEnum.TRANSPORT.getName()),
                new CategorySeed("Public Transport", CategoryTypeEnum.TRANSPORT.getName()),
                new CategorySeed("Salary", CategoryTypeEnum.JOB.getName()),
                new CategorySeed("Bonus", CategoryTypeEnum.JOB.getName()),
                new CategorySeed("Reliance Stocks", CategoryTypeEnum.STOCKS.getName()),
                new CategorySeed("HDFC Mutual Funds", CategoryTypeEnum.MUTUAL_FUNDS.getName()),
                new CategorySeed("HDFC Bank Savings", CategoryTypeEnum.SAVINGS_ACCOUNT.getName()),
                new CategorySeed("HDFC Fixed Deposit", CategoryTypeEnum.FIXED_DEPOSIT.getName()),
                new CategorySeed("HDFC Personal Loan 1", CategoryTypeEnum.PERSONAL_LOAN.getName())
                );
    }

    public record CategorySeed(String name, String type) {}
}