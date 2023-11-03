package com.eticket.configurations.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LicensePlateValidator.class)
public @interface LicensePlateFormat {
    String message() default "Invalid license plate format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
