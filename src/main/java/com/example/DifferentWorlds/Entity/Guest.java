package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.Roles;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
// TODO there is no need for guest entity as it does not have any action controller
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Roles Guest;
}
