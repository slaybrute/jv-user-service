package com.example.userservice.repository;

import com.example.userservice.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);

    void deleteById(@NonNull Long id);

    Optional<User> findByEmail(String email);

    List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}
