package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDTO {
    private Long paymentId;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
    private List<PurchaseResponseDTO> purchases;
}