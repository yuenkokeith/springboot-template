package com.microservices.accountservice.repository;

import com.microservices.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    // check if username exists
    Boolean existsByUsername(String username);

    // check if email exists
    Boolean existsByEmail(String email);
}
