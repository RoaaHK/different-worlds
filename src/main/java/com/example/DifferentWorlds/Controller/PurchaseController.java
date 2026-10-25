package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;
import com.example.DifferentWorlds.Service.CheckoutResult;
import com.example.DifferentWorlds.Service.PurchaseService;
import com.example.DifferentWorlds.dto.CheckoutRequestDTO;
import com.example.DifferentWorlds.dto.CheckoutResponseDTO;
import com.example.DifferentWorlds.dto.PurchaseResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@accessChecker.canAccessCustomer(#request.customerId)")
    public ResponseEntity<CheckoutResponseDTO> checkout(
            @RequestBody @Valid CheckoutRequestDTO request) {
        CheckoutResult result = purchaseService.checkout(
                request.getCustomerId(),
                request.getPaymentMethod()
        );
        return new ResponseEntity<>(mapToCheckoutDTO(result), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchaseHistory(
            @PathVariable Integer customerId) {
        List<PurchaseResponseDTO> history = purchaseService.getPurchaseHistory(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }

    private CheckoutResponseDTO mapToCheckoutDTO(CheckoutResult result) {
        List<PurchaseResponseDTO> purchases = result.purchases()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new CheckoutResponseDTO(
                result.payment().getPaymentId(),
                result.payment().getAmount(),
                result.payment().getPaymentMethod(),
                purchases
        );
    }

    private PurchaseResponseDTO mapToDTO(PurchaseHistoryEntity purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setHistoryId(purchase.getHistoryId());
        dto.setQuantity(purchase.getQuantity());
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