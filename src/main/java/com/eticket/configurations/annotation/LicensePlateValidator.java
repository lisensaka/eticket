package com.eticket.configurations.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class LicensePlateValidator implements ConstraintValidator<LicensePlateFormat, String> {
    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{2}\\d{3}[A-Z]{2}$");

    @Override
    public void initialize(LicensePlateFormat constraintAnnotation) {
        // Initialization, if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }
        return LICENSE_PLATE_PATTERN.matcher(value).matches();
    }
}
