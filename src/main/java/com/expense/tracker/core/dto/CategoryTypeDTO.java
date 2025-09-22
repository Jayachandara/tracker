package com.expense.tracker.core.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTypeDTO {
    private Long categoryTypeId;
    private Long userId;
    private String name;
}
