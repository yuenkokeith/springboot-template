package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.dto.RoleDto;
import com.microservices.accountservice.entity.Role;
import com.microservices.accountservice.exception.AccountAPIException;
import com.microservices.accountservice.repository.RoleRepository;
import com.microservices.accountservice.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public String saveRole(RoleDto roleDto) {

        // add check for username exist in database
        if(roleRepository.existsByName(roleDto.getName())) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Role name is already exists!.");
        }

        LOGGER.info("[saveRole] roleDto: {}", roleDto.getName());

        Role role = new Role();
        role.setName(roleDto.getName());
        role.setUpdatedAt(roleDto.getUpdatedAt());
        role.setCreatedAt(roleDto.getCreatedAt());

        roleRepository.save(role);

        return "Role created successfully!.";
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    @Override
    public Role findTopByOrderByIdAsc() {
        return roleRepository.findTopByOrderByIdAsc();
    }

    @Override
    public Role findTopByOrderByIdDesc() {
        return roleRepository.findTopByOrderByIdDesc();
    }
}
