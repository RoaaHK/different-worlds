package com.example.DifferentWorlds.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToOne
    private LiteraryWorksEntity literaryWork;

    private Integer quantity;

    private LocalDateTime addedDate;

    @PrePersist
    protected void onCreate() {
        this.addedDate = LocalDateTime.now();
    }
}