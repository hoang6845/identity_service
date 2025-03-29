package com.hoang.indentity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class DobValidator implements ConstraintValidator<Dob, LocalDate> {
    private int min, max;

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext constraintValidatorContext) {
        if (birthday == null){
            return true;
        }
        return ChronoUnit.YEARS.between(birthday, LocalDate.now()) >= min && ChronoUnit.YEARS.between(birthday, LocalDate.now()) <= max;
    }

    @Override
    public void initialize(Dob constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }
}
