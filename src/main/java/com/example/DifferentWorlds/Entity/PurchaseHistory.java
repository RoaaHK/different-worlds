package com.example.DifferentWorlds.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)private Integer id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private LiteraryWorks literaryWork;

    private Date purchaseDate;

    private double purchasePrice;
}
