package com.example.userservice.validator.date;

import java.time.LocalDate;

public interface BirthDateValidator {
    boolean isBirthDateValid(LocalDate birthDate);
}
