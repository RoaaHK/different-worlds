package com.example.DifferentWorlds.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    // TODO why using @Pattern when it's not a request in spring
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$",
            message = "Your email must follow specific format as this format ro.al@progressoft.io")
    private String email;

    @Size(min = 8, max = 20,message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "Password must have at least one digit, one lowercase, one uppercase letter, one special character and no whitespace")
    //@PasswordConstraints(message = "") won't use it now
    private String password;


}
