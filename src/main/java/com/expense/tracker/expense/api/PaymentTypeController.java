package com.expense.tracker.expense.api;

import com.expense.tracker.expense.external.dto.PaymentTypeDTO;
import com.expense.tracker.expense.internal.entity.PaymentType;
import com.expense.tracker.expense.internal.service.PaymentTypeService;
import com.expense.tracker.expense.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-types")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService service;

    @GetMapping
    public List<PaymentTypeDTO> getAll(@RequestParam Long userId) {
        return service.getAllByUser(userId).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeDTO> get(@PathVariable Long id) {
        return service.getById(id)
                .map(Mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PaymentTypeDTO create(@RequestParam Long userId, @RequestBody PaymentTypeDTO dto) {
        PaymentType entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.create(entity));
    }

    @PutMapping("/{id}")
    public PaymentTypeDTO update(@RequestParam Long userId, @PathVariable Long id, @RequestBody PaymentTypeDTO dto) {
        PaymentType entity = Mapper.toEntity(dto);
        entity.setUserId(userId);
        return Mapper.toDTO(service.update(userId, id, entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
