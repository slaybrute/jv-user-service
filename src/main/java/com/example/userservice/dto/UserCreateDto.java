package com.example.userservice.dto;

import com.example.userservice.validator.annotation.Date;
import com.example.userservice.validator.annotation.Email;
import com.example.userservice.validator.annotation.PhoneNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateDto(
        @NotNull
        @Email
        String email,
        @NotBlank(message = "Name must not be blank")
        String firstName,
        @NotBlank(message = "Surname must not be blank")
        String lastName,
        @NotNull(message = "Birth date must not be null")
        @Date
        String birthDate,
        @Nullable
        @Pattern(regexp = "^$|.*\\S.*", message = "Address must not be blank if provided")
        String address,
        @PhoneNumber
        String phoneNumber
) {
}
