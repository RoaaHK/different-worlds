package com.example.DifferentWorlds.Entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserBaseEntity {

    private String name;
    private String userName;
    private String password;
    private String email;
}