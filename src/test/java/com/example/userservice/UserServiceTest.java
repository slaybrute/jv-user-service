package com.example.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.example.userservice.converter.DateConverter;
import com.example.userservice.dto.UserCreateDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.dto.UserUpdateDto;
import com.example.userservice.exception.EntityAlreadyPresentException;
import com.example.userservice.exception.InvalidDateRangeException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserServiceTest {
    private static final String INVALID_NAME = null;
    private static final String INVALID_SURNAME = null;
    private static final String INVALID_EMAIL = null;
    private static final String INVALID_BIRTHDATE = "25-05-20022";
    private static UserCreateDto VALID_CREATE_USER_FULL;
    private static UserCreateDto VALID_CREATE_USER_NOT_FULL;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DateConverter dateConverter;

    @BeforeAll
    static void beforeAll() {
        VALID_CREATE_USER_FULL = new UserCreateDto(
                "test@gmail.com", "Don", "Warken",
                "11-12-2000", "Ukraine, Odesa", "+380685478520");
        VALID_CREATE_USER_NOT_FULL = new UserCreateDto(
                "test@gmail.com", "Andrean", "Berkin",
                "14-11-1998", null, null
        );
    }


    @Test
    void saveValidUserWithAllArgs_Ok() {
        UserResponseDto expected = new UserResponseDto("test@gmail.com", "Don", "Warken",
                "11-12-2000", "Ukraine, Odesa", "+380685478520");
        UserResponseDto actual = userService.save(VALID_CREATE_USER_FULL);
        assertEquals(expected, actual);
    }

    @Test
    void saveValidUserWithNotAllArgs_Ok() {
        UserResponseDto expected = new UserResponseDto("test@gmail.com", "Andrean", "Berkin",
                "14-11-1998", null, null);
        UserResponseDto actual = userService.save(VALID_CREATE_USER_NOT_FULL);
        assertEquals(expected, actual);
    }

    @Test
    void saveInvalidEmail_Not_Ok() {
        assertThrows(Exception.class, () -> {
            userService.save(new UserCreateDto(INVALID_EMAIL, "Andrean", "Berkin",
                    "14-11-1998", null, null));
        });
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void saveInvalidName_Not_Ok() {
        assertThrows(Exception.class, () -> {
            userService.save(new UserCreateDto("test@gmail.com", INVALID_NAME, "Berkin",
                    "14-11-1998", null, null));
        });
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void saveInvalidSurname_Not_Ok() {
        assertThrows(Exception.class, () -> {
            userService.save(new UserCreateDto("test@gmail.com", "Berun", INVALID_SURNAME,
                    "14-11-1998", null, null));
        });
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void saveInvalidBirthDate_Not_Ok() {
        assertThrows(Exception.class, () -> {
            userService.save(new UserCreateDto("test@gmail.com", "Artem", "Berkin",
                    INVALID_BIRTHDATE, null, null));
        });
        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void saveDuplicateEmail_Not_Ok() {
        userService.save(VALID_CREATE_USER_NOT_FULL);
        assertThrows(EntityAlreadyPresentException.class, () -> {
            userService.save(VALID_CREATE_USER_NOT_FULL);
        });
    }

    @Test
    void updateUserEmail_Ok() {
        userService.save(VALID_CREATE_USER_FULL);
        UserResponseDto expected = new UserResponseDto("new@gmail.com", "Don", "Warken",
                "11-12-2000", "Ukraine, Odesa", "+380685478520");
        UserResponseDto actual = userService.updateByEmail(
                VALID_CREATE_USER_FULL.email(), new UserUpdateDto("new@gmail.com",
                        null, null, null, null));
        assertEquals(expected, actual);
    }

    @Test
    void searchByValidRange_Ok() {
        userService.save(VALID_CREATE_USER_FULL);
        UserResponseDto expected = new UserResponseDto("test@gmail.com", "Don", "Warken",
                "11-12-2000", "Ukraine, Odesa", "+380685478520");
        UserResponseDto actual = userService.searchByBirthDateRange(
                dateConverter.toLocalDate("01-12-2000"), dateConverter.toLocalDate("25-12-2000")).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void searchByInvalidRange_Ok() {
        assertThrows(InvalidDateRangeException.class, () -> {
            userService.searchByBirthDateRange(
                    dateConverter.toLocalDate("25-12-2000"), dateConverter.toLocalDate("01-12-2000")).get(0);
        });
    }

    @Test
    @Transactional
    void deleteUserByEmail_Ok() {
        userService.save(VALID_CREATE_USER_FULL);
        System.out.println("hello");
        assertFalse(userRepository.findAll().isEmpty());
        userService.deleteByEmail(VALID_CREATE_USER_FULL.email());
        assertTrue(userRepository.findAll().isEmpty());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}
