package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.RoleDto;
import com.microservices.accountservice.entity.Role;
import com.microservices.accountservice.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/role")
public class RoleController {

    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveRole(@RequestBody RoleDto roleDto) {
        String response = roleService.saveRole(roleDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Role> getlatestRole() {
        Role role = roleService.findTopByOrderByIdDesc();
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("{orderBy}")
    public ResponseEntity<Role> getFirstRole(@PathVariable(name="orderBy") String orderby) {
        LOGGER.info("[getFirstRole] orderby {}", orderby);

        Role role = new Role();
        if(orderby.equals("asc")) {
            role = roleService.findTopByOrderByIdAsc();
        } else {
            role = roleService.findTopByOrderByIdDesc();
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
