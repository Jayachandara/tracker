package com.expense.tracker.expense.internal.repository;


import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.utilities.defaults.CategoryGroupEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {

    List<CategoryType> findByUserId(Long userId);

    Optional<CategoryType> findByUserIdAndName(Long userId, String name);

    boolean existsByUserId(Long userId);
}
