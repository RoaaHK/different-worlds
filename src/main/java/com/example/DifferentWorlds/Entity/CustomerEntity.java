package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends UserBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phoneNum;
    private Integer age;
    private Integer points;

    @Enumerated(EnumType.STRING)
    private Roles role;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @Temporal(TemporalType.DATE)
//    @Column(name = "purchases_history", nullable = true)
//    private Date purchasesHistory;
}