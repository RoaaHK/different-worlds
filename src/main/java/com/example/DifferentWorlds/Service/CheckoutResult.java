package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.PaymentEntity;
import com.example.DifferentWorlds.Entity.PurchaseHistoryEntity;

import java.util.List;

public record CheckoutResult(List<PurchaseHistoryEntity> purchases, PaymentEntity payment) {
}