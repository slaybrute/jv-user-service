package com.example.userservice.validator.phone;

import com.example.userservice.validator.annotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "^\\+\\d{8,15}$";

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        return number == null || Pattern.compile(PHONE_NUMBER_PATTERN).matcher(number).matches();
    }
}
