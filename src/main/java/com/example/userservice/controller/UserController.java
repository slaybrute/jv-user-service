package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateDto;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Save user to DB",
            description = "Save user to DB if there are all necessary fields valid: "
                    + "email, firstName, lastName, birthDate (should be >= 18 years old)")
    @PostMapping
    UserResponseDto save(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.save(userCreateDto);
    }

    @Operation(
            summary = "Update user by email",
            description = "Update any user field (by email user fetched from DB)"
    )
    @PutMapping("/{email}")
    UserResponseDto updateByEmail(
            @PathVariable String email, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return userService.updateByEmail(email, userUpdateDto);
    }

    @Operation(summary = "Delete user by id", description = "Delete user by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @Operation(summary = "Delete user by email", description = "Delete user by email")
    @DeleteMapping("/by-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByEmail(@RequestParam String email) {
        userService.deleteByEmail(email);
    }

    @Operation(summary = "Search users by birth range",
            description = "Search users by birth range (from; to) -> (less; bigger) date")
    @GetMapping("/by-birthdate")
    List<UserResponseDto> searchByDateInRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        return userService.searchByBirthDateRange(from, to);
    }
}
