package com.task.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PublicationYearCheck.class)
public @interface ValidPublishingYear {
    String message() default "Год публикации не может быть старше самой старой книги или в будущем";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
