package com.expense.tracker.expense.internal.entity;

import com.expense.tracker.expense.utilities.defaults.CategoryGroupEnum;
import com.expense.tracker.expense.utilities.defaults.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum categoryType;

    @Enumerated(EnumType.STRING)
    private CategoryGroupEnum categoryGroup;
}
