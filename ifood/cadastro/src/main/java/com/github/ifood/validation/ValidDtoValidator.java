package com.github.ifood.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDtoValidator implements ConstraintValidator<ValidDto, Dto> {

    @Override
    public void initialize(ValidDto constraintAnnotation) {
    }

    @Override
    public boolean isValid(Dto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.isValid(constraintValidatorContext);
    }

}
