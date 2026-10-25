package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Long paymentId;
    private Integer customerId;
    private String customerName;
    private Double amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdDate;
}