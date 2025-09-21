package com.expense.tracker.expense.internal.entity;

import com.expense.tracker.expense.utilities.defaults.CategoryGroupEnum;
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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CategoryType categoryType;

    @Enumerated(EnumType.STRING)
    private CategoryGroupEnum categoryGroup;
}
