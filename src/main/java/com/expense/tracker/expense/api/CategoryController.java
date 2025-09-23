package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.CategoryDTO;
import com.expense.tracker.core.dto.request.CategoryRequestDTO;
import com.expense.tracker.core.exception.InvalidRequestException;
import com.expense.tracker.core.exception.ResourceNotFoundException;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.utilities.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    // GET all categories for a user
    @GetMapping("/getAll")
    public List<CategoryDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    // GET category by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable Long id) {
        Category category = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return ResponseEntity.ok(Mapper.toDTO(category));
    }

    // CREATE category
    @PostMapping("/create")
    public CategoryDTO create(@Valid @RequestBody CategoryRequestDTO requestDTO) {
        // Map request DTO to entity
        Category entity = Mapper.toEntity(null, requestDTO);

        // Save and return DTO
        return Mapper.toDTO(service.create(entity));
    }

    // UPDATE category
    @PutMapping("/update/{id}")
    public CategoryDTO update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO requestDTO) {
        if (id == null) {
            throw new InvalidRequestException("Category ID is required for update");
        }

        Category existing = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        // Map request DTO to entity
        Category entity = Mapper.toEntity(id, requestDTO);
        entity.setCategoryId(id);
        entity.setUserId(existing.getUserId()); // preserve original userId

        return Mapper.toDTO(service.update(existing.getUserId(), id, entity));
    }

    // DELETE category
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
