package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.CartEntity;
import com.example.DifferentWorlds.Service.CartService;
import com.example.DifferentWorlds.dto.AddToCartRequestDTO;
import com.example.DifferentWorlds.dto.CartItemResponseDTO;
import com.example.DifferentWorlds.dto.MessageResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<List<CartItemResponseDTO>> getCart(@PathVariable Integer customerId) {
        List<CartItemResponseDTO> items = cartService.getCart(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{customerId}/items")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<CartItemResponseDTO> addToCart(
            @PathVariable Integer customerId,
            @RequestBody @Valid AddToCartRequestDTO request) {
        CartEntity entry = cartService.addToCart(
                customerId, request.getItemId(), request.getQuantity());
        return new ResponseEntity<>(mapToDTO(entry), HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}/items/{itemId}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<MessageResponseDTO> updateQuantity(
            @PathVariable Integer customerId,
            @PathVariable Integer itemId,
            @RequestParam int newQuantity) {
        cartService.updateQuantity(customerId, itemId, newQuantity);
        return ResponseEntity.ok(new MessageResponseDTO("Quantity updated."));
    }

    @DeleteMapping("/{customerId}/items/{itemId}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Integer customerId,
            @PathVariable Integer itemId) {
        cartService.removeFromCart(customerId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#customerId)")
    public ResponseEntity<Void> clearCart(@PathVariable Integer customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

    private CartItemResponseDTO mapToDTO(CartEntity entry) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartId(entry.getCartId());
        dto.setQuantity(entry.getQuantity());
        dto.setAddedDate(entry.getAddedDate());
        if (entry.getCustomer() != null) {
            dto.setCustomerId(entry.getCustomer().getId());
            dto.setCustomerName(entry.getCustomer().getName());
        }
        if (entry.getLiteraryWork() != null) {
            dto.setWorkId(entry.getLiteraryWork().getItemId());
            dto.setWorkTitle(entry.getLiteraryWork().getTitle());
            dto.setWorkPrice(entry.getLiteraryWork().getPrice());
        }
        return dto;
    }
}