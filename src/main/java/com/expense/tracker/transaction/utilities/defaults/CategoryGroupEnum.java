package com.expense.tracker.transaction.utilities.defaults;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // Map for fast lookup
    private static final Map<String, CategoryGroupEnum> NAME_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            categoryGroup -> categoryGroup.getName().toLowerCase(), // case-insensitive key
                            categoryGroup -> categoryGroup
                    ));


    // Optimized lookup by name
    public static CategoryGroupEnum getByName(String name) {
        if (name == null) return null;
        return NAME_MAP.get(name.toLowerCase());
    }

}
