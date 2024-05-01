package com.example.userservice.validator.date;

import com.example.userservice.validator.annotation.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<Date, String> {
    private static final String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])"
            + "-(0[1-9]|1[0-2])-([0-9]{4})$";

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        return date == null || Pattern.compile(DATE_PATTERN).matcher(date).matches();
    }
}
