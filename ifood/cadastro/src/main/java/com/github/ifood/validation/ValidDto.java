package com.github.ifood.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidDtoValidator.class })
@Documented
public @interface ValidDto {
	
	 String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
