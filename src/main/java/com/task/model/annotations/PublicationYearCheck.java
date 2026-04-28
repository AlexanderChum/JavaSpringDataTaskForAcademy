package com.task.model.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PublicationYearCheck implements ConstraintValidator<AfterEarliestBook, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value > -2363; //Prisse Papyrus publication date
    }
}
