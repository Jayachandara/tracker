package com.expense.tracker.expense.api;

import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.core.dto.request.TransactionRequestDTO;
import com.expense.tracker.core.exception.ResourceNotFoundException;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.Transaction;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.internal.service.TransactionService;
import com.expense.tracker.expense.utilities.Mapper;
import jakarta.validation.Valid;
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

    // GET all transactions for a user
    @GetMapping("/getAll")
    public List<TransactionDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    // GET transaction by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<TransactionDTO> get(@PathVariable Long id) {
        Transaction transaction = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
        return ResponseEntity.ok(Mapper.toDTO(transaction));
    }

    // CREATE transaction
    @PostMapping("/create")
    public TransactionDTO create(@Valid @RequestBody TransactionRequestDTO requestDTO) {
        // Validate category
        Category category = categoryService.getById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + requestDTO.getCategoryId()));

        // Map request DTO to entity
        Transaction entity = Mapper.toEntity(null, requestDTO, category);

        // Save transaction and return DTO
        return Mapper.toDTO(service.create(entity));
    }

    // UPDATE transaction
    @PutMapping("/update/{id}")
    public TransactionDTO update(@PathVariable Long id, @Valid @RequestBody TransactionRequestDTO requestDTO) {
        // Check if transaction exists
        Transaction existing = service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        // Validate category
        Category category = categoryService.getById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + requestDTO.getCategoryId()));

        // Map request DTO to entity
        Transaction entity = Mapper.toEntity(id, requestDTO, category);
        entity.setTransactionId(id);
        entity.setUserId(existing.getUserId()); // preserve original userId

        // Update transaction and return DTO
        return Mapper.toDTO(service.update(entity.getUserId(), id, entity));
    }

    // DELETE transaction
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
