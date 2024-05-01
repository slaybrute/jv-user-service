package com.example.userservice.service;

import com.example.userservice.dto.UserCreateDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateDto;
import com.example.userservice.exception.EntityAlreadyPresentException;
import com.example.userservice.exception.InvalidDateRangeException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserCreateDto userCreateDto) {
        if (userRepository.findByEmail(userCreateDto.email()).isPresent()) {
            throw new EntityAlreadyPresentException("User with such email exists: "
                    + userCreateDto.email());
        }
        return userMapper.toDto(
                userRepository.save(userMapper.toModel(userCreateDto)));
    }

    @Override
    public UserResponseDto updateByEmail(String email, UserUpdateDto userUpdateDto) {
        User prev = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Invalid user email: " + email));
        User next = userMapper.toModel(userUpdateDto);
        next.setId(prev.getId());
        next.setBirthDate(prev.getBirthDate());
        if (next.getEmail() == null) {
            next.setEmail(prev.getEmail());
        }
        if (next.getFirstName() == null) {
            next.setFirstName(prev.getFirstName());
        }
        if (next.getLastName() == null) {
            next.setLastName(prev.getLastName());
        }
        if (next.getAddress() == null) {
            next.setAddress(prev.getAddress());
        }
        if (next.getPhoneNumber() == null) {
            next.setPhoneNumber(prev.getPhoneNumber());
        }
        return userMapper.toDto(userRepository.save(next));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public List<UserResponseDto> searchByBirthDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new InvalidDateRangeException(from + " should be after " + to);
        }
        return userRepository.findAllByBirthDateBetween(from, to)
                .stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
