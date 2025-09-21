package com.expense.tracker.expense.internal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "category_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_group_id")
    private Long categoryGroupId;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // INCOME, SAVINGS, INVESTMENT, EXPENSES

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "categoryGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CategoryType> categoryTypes;
}
