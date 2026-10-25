package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.CustomerEntity;
import com.example.DifferentWorlds.Service.CustomerService;
import com.example.DifferentWorlds.dto.MessageResponseDTO;
import com.example.DifferentWorlds.dto.UpdateEmailRequestDTO;
import com.example.DifferentWorlds.dto.UpdatePasswordRequestDTO;
import com.example.DifferentWorlds.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#id)")
    public ResponseEntity<UserResponseDTO> getCustomerById(@PathVariable Integer id) {
        CustomerEntity customer = customerService.getCustomer(id);
        return ResponseEntity.ok(mapToDTO(customer));
    }

    @GetMapping("/by-username/{userName}")
    @PreAuthorize("@accessChecker.canAccessCustomerByUserName(#userName)")
    public ResponseEntity<UserResponseDTO> getCustomerByUserName(@PathVariable String userName) {
        CustomerEntity customer = customerService.getCustomerByUserName(userName);
        return ResponseEntity.ok(mapToDTO(customer));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllCustomers() {
        List<UserResponseDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("@accessChecker.canAccessCustomer(#id)")
    public ResponseEntity<MessageResponseDTO> updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UpdatePasswordRequestDTO request) {
        customerService.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok(new MessageResponseDTO("Password updated successfully."));
    }

    @PatchMapping("/{id}/email")
    @PreAuthorize("@accessChecker.canAccessCustomer(#id)")
    public ResponseEntity<MessageResponseDTO> updateEmail(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateEmailRequestDTO request) {
        customerService.updateEmail(id, request.getNewEmail());
        return ResponseEntity.ok(new MessageResponseDTO("Email updated successfully."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessChecker.canAccessCustomer(#id)")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponseDTO mapToDTO(CustomerEntity customer) {
        return new UserResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getUserName(),
                customer.getEmail(),
                "CUSTOMER"
        );
    }
}