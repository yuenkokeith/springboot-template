package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.RegisterMemberMasterCounterDto;
import com.microservices.accountservice.service.RegisterMemberMasterCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://api-gateway:9191", "http://localhost:9191"}, maxAge = 3600)
@RequestMapping("api/v1/registermembermastercounter")
public class RegisterMemberMasterCounterController {

    Logger LOGGER = LoggerFactory.getLogger(RegisterMemberMasterCounterController.class);

    @Autowired
    RegisterMemberMasterCounterService registerMemberMasterCounterService;

    @GetMapping("{orderBy}")
    public ResponseEntity<RegisterMemberMasterCounterDto> getFirstRegisterMemberMasterCounter(@PathVariable(name="orderBy") String orderby) {
        LOGGER.info("[getFirstRegisterMemberMasterCounter] orderby {}", orderby);

        RegisterMemberMasterCounterDto registerMemberMasterCounterDto = new RegisterMemberMasterCounterDto();
        if(orderby.equals("asc")) {
            registerMemberMasterCounterDto = registerMemberMasterCounterService.findTopByOrderByIdAsc();
        } else {
            registerMemberMasterCounterDto = registerMemberMasterCounterService.findTopByOrderByIdDesc();
        }
        LOGGER.info("[getFirstRegisterMemberMasterCounter] registerMemberMasterCounter value: {}", registerMemberMasterCounterDto);
        if(registerMemberMasterCounterDto==null) {
            LOGGER.info("[getFirstRegisterMemberMasterCounter] registerMemberMasterCounter IS NULL");
        } else {
            LOGGER.info("[getFirstRegisterMemberMasterCounter] registerMemberMasterCounter IS NOT NULL");
        }

        return new ResponseEntity<>(registerMemberMasterCounterDto, HttpStatus.OK);
    }

}
