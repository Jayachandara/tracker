package com.expense.tracker.expense.utilities.defaults;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CategoryGroupEnum {
    INCOME("Income"),
    SAVINGS("Savings"),
    INVESTMENT("Investment"),
    EXPENSES("Expenses"),
    LOANS("Loans");

    private final String name;

    CategoryGroupEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<CategoryTypeEnum> getChildCategories() {
        return Arrays.stream(CategoryTypeEnum.values())
                .filter(c -> c.getGroup() == this)
                .collect(Collectors.toList());
    }

}
