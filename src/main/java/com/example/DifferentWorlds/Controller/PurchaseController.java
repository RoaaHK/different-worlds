package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import com.example.DifferentWorlds.Service.PurchaseService;
import com.example.DifferentWorlds.dto.PurchaseRequestDTO;
import com.example.DifferentWorlds.dto.PurchaseResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> addPurchase(
            @RequestBody @Valid PurchaseRequestDTO request) {
        PurchaseHistoryEntity purchase = purchaseService.addPurchase(
                request.getCustomerId(),
                request.getLiteraryWorkId(),
                request.getPurchasePrice()
        );
        return new ResponseEntity<>(mapToDTO(purchase), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchaseHistory(
            @PathVariable Integer customerId) {
        List<PurchaseResponseDTO> history = purchaseService.getPurchaseHistory(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }

    private PurchaseResponseDTO mapToDTO(PurchaseHistoryEntity purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setHistoryId(purchase.getHistoryId());
        dto.setPurchasePrice(purchase.getPurchasePrice());
        dto.setPurchaseDate(purchase.getPurchaseDate());

        if (purchase.getCustomer() != null) {
            dto.setCustomerId(purchase.getCustomer().getId());
            dto.setCustomerName(purchase.getCustomer().getName());
        }

        if (purchase.getLiteraryWork() != null) {
            dto.setWorkId(purchase.getLiteraryWork().getItemId());
            dto.setWorkTitle(purchase.getLiteraryWork().getTitle());
        }

        return dto;
    }
}