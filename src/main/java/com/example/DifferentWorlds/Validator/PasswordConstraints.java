package com.example.DifferentWorlds.Validator;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraints {

    String message() default "Invalid Password Format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

