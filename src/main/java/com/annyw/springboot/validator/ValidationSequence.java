package com.annyw.springboot.validator;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, EmailPattern.class, UsernameNotBlank.class, UsernamePattern.class,
    UsernameValidator.class, PasswordNotBlank.class, LengthReq.class,
    PasswordPattern.class})
public interface ValidationSequence {
}
