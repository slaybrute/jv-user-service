package com.example.userservice.dto;

import com.example.userservice.validator.annotation.Email;
import com.example.userservice.validator.annotation.PhoneNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDto(
        @Email
        String email,
        @Nullable
        @Pattern(regexp = "^$|.*\\S.*", message = "Name must not be blank if provided")
        String firstName,
        @Nullable
        @Pattern(regexp = "^$|.*\\S.*", message = "Surname must not be blank if provided")
        String lastName,
        @Nullable
        @Pattern(regexp = "^$|.*\\S.*", message = "Address must not be blank if provided")
        String address,
        @PhoneNumber
        String phoneNumber
) {
}
// We are not able to update birthdate (it is fixed)
