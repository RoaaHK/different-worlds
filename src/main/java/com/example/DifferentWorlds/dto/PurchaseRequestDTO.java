package com.example.DifferentWorlds.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDTO {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Literary work ID is required")
    private Integer literaryWorkId;

    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be greater than zero")
    private Double purchasePrice;
}