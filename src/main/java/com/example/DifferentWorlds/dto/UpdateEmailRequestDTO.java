package com.example.DifferentWorlds.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid format")
    private String newEmail;
}