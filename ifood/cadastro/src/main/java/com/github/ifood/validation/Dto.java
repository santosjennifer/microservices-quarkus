package com.github.ifood.validation;

import jakarta.validation.ConstraintValidatorContext;

public interface Dto {

    default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
    
}
