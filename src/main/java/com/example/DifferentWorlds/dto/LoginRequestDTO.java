package com.example.DifferentWorlds.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO{
    private String userName;
    private String email;
    private String password;

}
