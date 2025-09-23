package com.expense.tracker.core.dto.request;

import com.expense.tracker.transaction.utilities.defaults.CategoryGroupEnum;
import com.expense.tracker.transaction.utilities.defaults.CategoryTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    private String name;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Category Type ID is required")
    private String categoryType;

}
