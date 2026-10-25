package com.example.DifferentWorlds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private Long cartId;
    private Integer customerId;
    private String customerName;
    private Integer workId;
    private String workTitle;
    private Double workPrice;
    private Integer quantity;
    private LocalDateTime addedDate;
}