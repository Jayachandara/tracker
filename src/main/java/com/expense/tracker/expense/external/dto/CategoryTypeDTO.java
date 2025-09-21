package com.expense.tracker.expense.external.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTypeDTO {
    private Long categoryTypeId;
    private Long userId;
    private Long categoryGroupId;
    private String name;
    private String description;
}
