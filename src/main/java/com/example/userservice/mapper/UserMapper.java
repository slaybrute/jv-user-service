package com.example.userservice.mapper;

import com.example.userservice.config.MapperConfig;
import com.example.userservice.converter.DateConverter;
import com.example.userservice.converter.DateConverterImpl;
import com.example.userservice.dto.UserCreateDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateDto;
import com.example.userservice.exception.InvalidBirthDateException;
import com.example.userservice.model.User;
import com.example.userservice.validator.date.BirthDateValidator;
import com.example.userservice.validator.date.BirthDateValidatorImpl;
import java.time.LocalDate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    DateConverter DATE_CONVERTER = new DateConverterImpl();
    BirthDateValidator BIRTH_DATE_VALIDATOR = new BirthDateValidatorImpl();

    @Mapping(target = "birthDate", ignore = true)
    UserResponseDto toDto(User user);

    @Mapping(target = "birthDate", ignore = true)
    User toModel(UserCreateDto userCreateDto);

    User toModel(UserUpdateDto userUpdateDto);

    @AfterMapping
    default void setBirthDate(@MappingTarget UserResponseDto userDto, User user) {
        LocalDate date = user.getBirthDate();
        String convertedDate = date.getDayOfMonth() + "-"
                + date.getMonthValue() + "-" + date.getYear();
        userDto.setBirthDate(convertedDate);
    }

    @AfterMapping
    default void setBirthDate(@MappingTarget User user, UserCreateDto userCreateDto) {
        LocalDate birthDate = DATE_CONVERTER.toLocalDate(userCreateDto.birthDate());
        if (!BIRTH_DATE_VALIDATOR.isBirthDateValid(birthDate)) {
            throw new InvalidBirthDateException("You must be of legal age (>= 18)");
        }
        user.setBirthDate(birthDate);
    }
}
