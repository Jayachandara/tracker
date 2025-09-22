package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.CategoryTypeDTO;
import com.expense.tracker.core.dto.request.CategoryTypeRequestDTO;
import com.expense.tracker.core.exception.InvalidRequestException;
import com.expense.tracker.core.exception.ResourceNotFoundException;
import com.expense.tracker.expense.internal.entity.CategoryType;
import com.expense.tracker.expense.internal.service.CategoryTypeService;
import com.expense.tracker.expense.utilities.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-types")
@RequiredArgsConstructor
public class CategoryTypeController {

    private final CategoryTypeService service;

    // GET all category types for a user
    @GetMapping("/getAll")
    public List<CategoryTypeDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    // GET category type by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryTypeDTO> get(@PathVariable Long id) {
        CategoryType entity = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryType not found with ID: " + id));
        return ResponseEntity.ok(Mapper.toDTO(entity));
    }

    // CREATE category type
    @PostMapping("/create")
    public CategoryTypeDTO create(@Valid @RequestBody CategoryTypeRequestDTO requestDTO) {
        CategoryType entity = Mapper.toEntity(null, requestDTO); // Map request DTO to entity
        return Mapper.toDTO(service.create(entity));
    }

    // UPDATE category type
    @PutMapping("/update/{id}")
    public CategoryTypeDTO update(@PathVariable Long id, @Valid @RequestBody CategoryTypeRequestDTO requestDTO) {
        if (id == null) {
            throw new InvalidRequestException("CategoryType ID is required for update");
        }

        CategoryType existing = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoryType not found with ID: " + id));

        CategoryType entity = Mapper.toEntity(id, requestDTO); // Map request DTO to entity
        entity.setCategoryTypeId(id);
        entity.setUserId(existing.getUserId()); // preserve original userId

        return Mapper.toDTO(service.update(existing.getUserId(), id, entity));
    }

    // DELETE category type
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
