package com.expense.tracker.core.dto;

import com.expense.tracker.transaction.utilities.defaults.CategoryGroupEnum;
import com.expense.tracker.transaction.utilities.defaults.CategoryTypeEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private Long userId;
    private CategoryTypeEnum categoryType;
    private CategoryGroupEnum categoryGroup;
}
