package com.expense.tracker.expense.external.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGroupDTO {
    private Long categoryGroupId;
    private Long userId;
    private String name;
    private String description;
}
