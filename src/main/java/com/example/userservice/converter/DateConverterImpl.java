package com.example.userservice.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateConverterImpl implements DateConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // dd-MM-yyy pattern
    @Override
    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }
}
