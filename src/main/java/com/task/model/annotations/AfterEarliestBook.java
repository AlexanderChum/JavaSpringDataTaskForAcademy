package com.task.model.annotations;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PublicationYearCheck.class)
public @interface AfterEarliestBook {
    String message() default "Год публикации не может быть старше самой старой книги";
}
