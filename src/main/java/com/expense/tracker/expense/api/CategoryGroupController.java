package com.expense.tracker.expense.api;


import com.expense.tracker.expense.external.dto.CategoryGroupDTO;
import com.expense.tracker.expense.internal.service.CategoryGroupService;
import com.expense.tracker.expense.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-groups")
@RequiredArgsConstructor
public class CategoryGroupController {

    private final CategoryGroupService service;

    @GetMapping
    public List<CategoryGroupDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryGroupDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CategoryGroupDTO create(@RequestParam Long userId, @RequestBody CategoryGroupDTO dto) {
        var entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/{id}")
    public CategoryGroupDTO update(@RequestParam Long userId, @PathVariable Long id, @RequestBody CategoryGroupDTO dto) {
        var entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.update(userId, id, entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
