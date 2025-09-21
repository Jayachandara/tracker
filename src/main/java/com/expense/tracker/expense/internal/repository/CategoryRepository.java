package com.expense.tracker.expense.internal.repository;

import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    List<Category> findByUserIdAndCategoryType_Id(Long userId, Long typeId);

    Optional<Category> findByUserIdAndName(Long userId, String name);

    boolean existsByUserId(Long userId);

}
