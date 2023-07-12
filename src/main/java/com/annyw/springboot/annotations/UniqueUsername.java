package com.annyw.springboot.annotations;

import com.annyw.springboot.validator.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface UniqueUsername {
    
    String message() default "Username already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
