package com.expense.tracker.expense.api;

import com.expense.tracker.expense.external.dto.CategoryTypeDTO;
import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.internal.service.CategoryTypeService;
import com.expense.tracker.expense.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-types")
@RequiredArgsConstructor
public class CategoryTypeController {

    private final CategoryTypeService service;
    @GetMapping
    public List<CategoryTypeDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryTypeDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategoryTypeDTO create(@RequestParam Long userId, @RequestBody CategoryTypeDTO dto) {
        CategoryType entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/{id}")
    public CategoryTypeDTO update(@RequestParam Long userId, @PathVariable Long id, @RequestBody CategoryTypeDTO dto) {
        CategoryType entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.update(userId, id, entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
