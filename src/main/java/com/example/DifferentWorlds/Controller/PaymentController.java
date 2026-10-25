package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.PaymentEntity;
import com.example.DifferentWorlds.Service.PaymentService;
import com.example.DifferentWorlds.dto.PaymentResponseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        // getPaymentById throws EntityNotFoundException if not found — no Optional needed
        PaymentEntity payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(mapToDTO(payment));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/between")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentResponseDTO> payments = paymentService.getPaymentsBetween(startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(payments);
    }

    private PaymentResponseDTO mapToDTO(PaymentEntity payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setCreatedDate(payment.getCreatedDate());
        if (payment.getCustomer() != null) {
            dto.setCustomerId(payment.getCustomer().getId());
            dto.setCustomerName(payment.getCustomer().getName());
        }
        return dto;
    }
}