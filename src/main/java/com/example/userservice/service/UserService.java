package com.example.userservice.service;

import com.example.userservice.dto.UserCreateDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserResponseDto save(UserCreateDto userCreateDto);

    UserResponseDto updateByEmail(String email, UserUpdateDto userUpdateDto);

    void deleteById(Long id);

    @Transactional
    void deleteByEmail(String email);

    List<UserResponseDto> searchByBirthDateRange(LocalDate from, LocalDate to);
}
