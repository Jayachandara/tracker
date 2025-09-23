package com.expense.tracker.transaction.api;

import com.expense.tracker.core.dto.TransactionDTO;
import com.expense.tracker.core.dto.request.TransactionRequestDTO;
import com.expense.tracker.core.exception.InvalidRequestException;
import com.expense.tracker.core.exception.ResourceNotFoundException;
import com.expense.tracker.transaction.internal.entity.Category;
import com.expense.tracker.transaction.internal.entity.Transaction;
import com.expense.tracker.transaction.internal.service.CategoryService;
import com.expense.tracker.transaction.internal.service.TransactionService;
import com.expense.tracker.transaction.utilities.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
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

    @GetMapping("/getByDateRange")
    public List<TransactionDTO> getByDateRange(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        // Parse dates from request (expecting ISO format: yyyy-MM-dd'T'HH:mm:ss)
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = LocalDateTime.parse(startDate);
            end = LocalDateTime.parse(endDate);
        } catch (Exception e) {
            throw new InvalidRequestException("Invalid date format. Use yyyy-MM-dd'T'HH:mm:ss");
        }

        // Fetch transactions from service
        return service.getByUserAndDateRange(userId, start, end).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/getByYear")
    public List<TransactionDTO> getByYear(
            @RequestParam Long userId,
            @RequestParam int year
    ) {
        if (year < 1900 || year > 3000) {
            throw new InvalidRequestException("Invalid year: " + year);
        }

        // Compute start and end of the year
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        // Fetch transactions from service
        return service.getByUserAndDateRange(userId, startOfYear, endOfYear).stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @GetMapping("/getByMonth")
    public List<TransactionDTO> getByMonth(
            @RequestParam Long userId,
            @RequestParam int month,
            @RequestParam(required = false) Integer year
    ) {
        int targetYear = (year != null) ? year : java.time.Year.now().getValue();

        if (month < 1 || month > 12) {
            throw new InvalidRequestException("Invalid month: " + month + ". Must be between 1 and 12.");
        }

        // Compute start and end of the month
        YearMonth yearMonth = YearMonth.of(targetYear, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return service.getByUserAndDateRange(userId, startOfMonth, endOfMonth).stream()
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

        if (id == null) {
            throw new InvalidRequestException("Transaction ID is required for update");
        }

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
