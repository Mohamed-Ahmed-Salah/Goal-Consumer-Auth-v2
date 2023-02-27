package com.gconsumer.GConsumer.validatation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "fullName", "name must not be null, empty ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email must not be null, empty or white spaced");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phone must not be null, empty or white spaced");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "name must not be null, empty or white spaced");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "name must not be null, empty or white spaced");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "name must not be null, empty or white spaced");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "name must not be null, empty or white spaced");
    }
}
