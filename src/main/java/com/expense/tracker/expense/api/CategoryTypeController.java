package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.CategoryTypeDTO;
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
    @GetMapping("/getAll")
    public List<CategoryTypeDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<CategoryTypeDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public CategoryTypeDTO create(@RequestBody CategoryTypeDTO dto) {
        // Map DTO to entity
        CategoryType entity = Mapper.toEntity(dto);

        // userId comes from JSON body
        entity.setUserId(dto.getUserId());

        // Save and return DTO
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/update")
    public CategoryTypeDTO update(@RequestBody CategoryTypeDTO dto) {
        if (dto.getCategoryTypeId() == null) {
            throw new IllegalArgumentException("CategoryType ID is required for update");
        }

        // Map DTO to entity
        CategoryType entity = Mapper.toEntity(dto);

        // userId comes from JSON body
        entity.setUserId(dto.getUserId());

        // Update and return DTO
        return Mapper.toDTO(service.update(dto.getUserId(), dto.getCategoryTypeId(), entity));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
