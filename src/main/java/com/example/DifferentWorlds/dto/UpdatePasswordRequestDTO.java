package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Validator.PasswordConstraints;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {

    @NotBlank(message = "Password is required")
    @PasswordConstraints
    private String newPassword;
}