package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.RoleDto;
import com.microservices.accountservice.entity.Role;
import com.microservices.accountservice.repository.RoleRepository;

public interface RoleService {
    String saveRole(RoleDto roleDto);
    RoleRepository getRoleRepository();
    Role findTopByOrderByIdAsc();
    Role findTopByOrderByIdDesc();
}
