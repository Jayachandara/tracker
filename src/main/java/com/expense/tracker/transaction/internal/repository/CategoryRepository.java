package com.expense.tracker.transaction.internal.repository;

import com.expense.tracker.transaction.internal.entity.Category;
import com.expense.tracker.transaction.utilities.defaults.CategoryTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    List<Category> findByUserIdAndCategoryType(Long userId, CategoryTypeEnum type);

    Optional<Category> findByUserIdAndName(Long userId, String name);

    boolean existsByUserId(Long userId);

}
