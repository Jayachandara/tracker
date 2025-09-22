package com.expense.tracker.expense.utilities.defaults;

import com.expense.tracker.core.event.UserCreatedEvent;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.internal.repository.CategoryRepository;
import com.expense.tracker.expense.internal.repository.CategoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryInitializationService {

    private final CategoryTypeRepository categoryTypeRepository;
    private final CategoryRepository categoryRepository;

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        Long userId = event.userId();
        initializeDefaultsForUser(userId);
    }

    @Transactional
    public void initializeDefaultsForUser(Long userId) {
        // --- 1. Initialize CategoryTypes ---
        boolean typesExist = categoryTypeRepository.existsByUserId(userId);

        if (!typesExist) {
            List<CategoryType> typesToSave = Arrays.stream(CategoryTypeEnum.values())
                    .map(typeEnum -> CategoryType.builder()
                            .userId(userId)
                            .name(typeEnum.name())
                            .build())
                    .toList();

            categoryTypeRepository.saveAll(typesToSave);
        }

        // --- 2. Initialize Categories ---
        boolean categoriesExist = categoryRepository.existsByUserId(userId);

        if (!categoriesExist) {
            // fetch all CategoryTypes for user in one query
            List<CategoryType> userCategoryTypes = categoryTypeRepository.findByUserId(userId);
            Map<String, CategoryType> typeMap = userCategoryTypes.stream()
                    .collect(Collectors.toMap(CategoryType::getName, ct -> ct));

            List<Category> categoriesToSave = CategoryDefaults.getDefaults().stream()
                    .map(seed -> Category.builder()
                            .userId(userId)
                            .name(seed.name())
                            .categoryType(typeMap.get(seed.type().name()))
                            .categoryGroup(seed.group())
                            .build())
                    .toList();

            categoryRepository.saveAll(categoriesToSave);
        }
    }
}

