package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.CategoryDTO;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.internal.service.CategoryTypeService;
import com.expense.tracker.expense.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryTypeService typeService;

    @GetMapping("/getAll")
    public List<CategoryDTO> getAll(@RequestParam Long userId) {

        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public CategoryDTO create(@RequestParam Long userId, @RequestBody CategoryDTO dto) {
        CategoryType type = typeService.getById(dto.getCategoryTypeId())
                .orElseThrow(() -> new RuntimeException("CategoryType not found"));
        Category entity = Mapper.toEntity(dto, type);
        entity.setUserId(userId);
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/update/{id}")
    public CategoryDTO update(@RequestParam Long userId, @PathVariable Long id, @RequestBody CategoryDTO dto) {
        CategoryType type = typeService.getById(dto.getCategoryTypeId())
                .orElseThrow(() -> new RuntimeException("CategoryType not found"));
        Category entity = Mapper.toEntity(dto, type);
        entity.setUserId(userId);
        return Mapper.toDTO(service.update(userId, id, entity));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
