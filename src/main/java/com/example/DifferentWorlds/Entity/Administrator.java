package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
// TODO replace GeneralInformation interface with abstract User class
// TODO for naming convention make the entity class end with Entity i.e AdministratorEntity
public class Administrator implements GeneralInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String userName;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles roles;

}

