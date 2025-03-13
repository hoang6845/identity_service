package com.hoang.indentity_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeOver18Validator implements ConstraintValidator<AgeOver18, LocalDate> {
    @Override
    public void initialize(AgeOver18 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthDay, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDay == null) {
            return false;
        }
        return Period.between(birthDay, LocalDate.now()).getYears() >= 18;
    }
}
