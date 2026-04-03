package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.PaymentsMethods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    private Customer customer;

    private double amount;
    private String password;

    @Enumerated(EnumType.STRING)
    private PaymentsMethods paymentsMethods;

    private LocalDateTime createdDate = LocalDateTime.now();
}
