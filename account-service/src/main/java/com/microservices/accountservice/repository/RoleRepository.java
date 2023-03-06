package com.microservices.accountservice.repository;

import com.microservices.accountservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    // check if name exists
    Boolean existsByName(String name);

    Role findTopByOrderByIdAsc();
    Role findTopByOrderByIdDesc();
}
