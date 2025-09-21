package com.expense.tracker.expense.external.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long categoryId;
    private Long userId;
    private Long categoryTypeId;
    private String name;
    private String description;
}
