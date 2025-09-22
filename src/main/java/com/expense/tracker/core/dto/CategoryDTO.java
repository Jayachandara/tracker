package com.expense.tracker.core.dto;

import com.expense.tracker.expense.utilities.defaults.CategoryGroupEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private Long userId;
    private Long categoryTypeId;
    private CategoryGroupEnum categoryGroup;
}
