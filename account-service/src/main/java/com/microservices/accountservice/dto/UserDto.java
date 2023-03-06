package com.microservices.accountservice.dto;

import com.microservices.accountservice.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    @NotEmpty
    @Size(min=4, message = "Name should have at least 4 characters")
    private String name;

    @NotEmpty
    @Size(min=4, message = "Username should have at least 4 characters")
    private String username;

    @Email
    private String email;

    @NotEmpty
    @Size(min=4, message = "Password must be at least 4 characters")
    private String password;

    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
