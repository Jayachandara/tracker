package com.expense.tracker.expense.api;

import com.expense.tracker.expense.external.dto.TransactionDTO;
import com.expense.tracker.expense.internal.entity.Category;
import com.expense.tracker.expense.internal.entity.PaymentType;
import com.expense.tracker.expense.internal.entity.Transaction;
import com.expense.tracker.expense.internal.service.CategoryService;
import com.expense.tracker.expense.internal.service.PaymentTypeService;
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
    private final PaymentTypeService paymentTypeService;

    @GetMapping
    public List<TransactionDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TransactionDTO create(@RequestParam Long userId, @RequestBody TransactionDTO dto) {
        Category category = categoryService.getById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        PaymentType paymentType = paymentTypeService.getById(dto.getPaymentTypeId())
                .orElseThrow(() -> new RuntimeException("PaymentType not found"));
        Transaction entity = Mapper.toEntity(dto, category, paymentType);
        entity.setUserId(userId);
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@RequestParam Long userId, @PathVariable Long id, @RequestBody TransactionDTO dto) {
        Category category = categoryService.getById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        PaymentType paymentType = paymentTypeService.getById(dto.getPaymentTypeId())
                .orElseThrow(() -> new RuntimeException("PaymentType not found"));
        Transaction entity = Mapper.toEntity(dto, category, paymentType);
        entity.setUserId(userId);
        return Mapper.toDTO(service.update(userId, id, entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}

