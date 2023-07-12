package com.annyw.springboot.validator;

import com.annyw.springboot.annotations.UniqueUsername;
import com.annyw.springboot.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private UserService userService;
    public void initialize(UniqueUsername username) {
    }
    
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.usernameExists(username);
    }
}
