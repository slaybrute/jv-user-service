package com.example.userservice.validator.email;

import com.example.userservice.validator.annotation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9"
            + "_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-"
            + "]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email == null || Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}
