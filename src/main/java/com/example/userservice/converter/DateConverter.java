package com.example.userservice.converter;

import java.time.LocalDate;

public interface DateConverter {
    LocalDate toLocalDate(String date);
}
