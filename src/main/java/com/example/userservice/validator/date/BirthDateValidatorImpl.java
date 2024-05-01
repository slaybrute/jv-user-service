package com.example.userservice.validator.date;

import java.time.LocalDate;

public class BirthDateValidatorImpl implements BirthDateValidator {
    @Override
    public boolean isBirthDateValid(LocalDate birthDate) {
        LocalDate current = LocalDate.now();
        if (current.getYear() - birthDate.getYear() > 18) {
            return true;
        } else if (current.getYear() - birthDate.getYear() < 18) {
            return false;
        } else {
            if (current.getMonthValue() > birthDate.getMonthValue()) {
                return true;
            } else if (current.getMonthValue() < birthDate.getMonthValue()) {
                return false;
            } else {
                return current.getDayOfMonth() > birthDate.getDayOfMonth();
            }
        }
    }
}
