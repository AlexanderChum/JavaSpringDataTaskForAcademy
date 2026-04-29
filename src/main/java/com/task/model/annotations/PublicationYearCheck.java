package com.task.model.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class PublicationYearCheck implements ConstraintValidator<ValidPublishingYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value > -2363 && value <= Year.now().getValue(); //-2363 is Prisse Papyrus publication date
    }
}
