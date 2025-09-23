package com.expense.tracker.transaction.utilities.defaults;

import com.expense.tracker.core.event.UserCreatedEvent;
import com.expense.tracker.transaction.internal.entity.Category;
import com.expense.tracker.transaction.internal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInitializationService {

    private final CategoryRepository categoryRepository;

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        Long userId = event.userId();
        initializeDefaultsForUser(userId);
    }

    @Transactional
    public void initializeDefaultsForUser(Long userId) {
        // --- 1. Initialize Categories ---
        boolean categoriesExist = categoryRepository.existsByUserId(userId);

        if (!categoriesExist) {
            List<Category> categoriesToSave = CategoryDefaults.getDefaults().stream()
                    .map(seed -> Category.builder()
                            .userId(userId)
                            .name(seed.name())                  // enum name
                            .categoryType(seed.type())          // CategoryTypeEnum
                            .categoryGroup(seed.group())        // CategoryGroupEnum
                            .build())
                    .toList();

            categoryRepository.saveAll(categoriesToSave);
        }
    }
}

