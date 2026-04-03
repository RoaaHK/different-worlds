package com.example.DifferentWorlds.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequest {
    private long customerId;
    private long literaryWorkId;
    private double purchasePrice;
}
