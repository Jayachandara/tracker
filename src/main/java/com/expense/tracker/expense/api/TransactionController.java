package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.Transaction;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.internal.service.TransactionService;
import com.expense.tracker.expense.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public List<TransactionDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TransactionDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public TransactionDTO create(@RequestBody TransactionDTO dto) {
        // Validate category
        Category category = categoryService.getById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Map DTO to entity
        Transaction entity = Mapper.toEntity(dto, category);

        // userId comes from JSON body, no need for @RequestParam
        entity.setUserId(dto.getUserId());

        // Save and return DTO
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/update")
    public TransactionDTO update(@RequestBody TransactionDTO dto) {
        if (dto.getTransactionId() == null) {
            throw new IllegalArgumentException("Transaction ID is required for update");
        }

        // Validate category
        Category category = categoryService.getById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Map DTO to entity
        Transaction entity = Mapper.toEntity(dto, category);

        // userId comes from JSON body
        entity.setUserId(dto.getUserId());

        // Update and return DTO
        return Mapper.toDTO(service.update(dto.getUserId(), dto.getTransactionId(), entity));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}

