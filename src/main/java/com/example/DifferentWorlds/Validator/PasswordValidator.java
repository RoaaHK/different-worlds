package com.example.DifferentWorlds.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraints, String>  {

    @Override
    public void initialize(PasswordConstraints passwordConstraints) {
    }

    @Override
    public boolean isValid(String pass, ConstraintValidatorContext cxt) {
        if (pass == null) {
            return false;
        }
        ///
        return pass.matches("my reg exp");
    }
}
