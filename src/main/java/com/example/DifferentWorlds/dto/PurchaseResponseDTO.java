package com.example.DifferentWorlds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO {
    private Long historyId;
    private Integer customerId;
    private String customerName;
    private Integer workId;
    private String workTitle;
    private Integer quantity;
    private Double purchasePrice;
    private LocalDateTime purchaseDate;
}