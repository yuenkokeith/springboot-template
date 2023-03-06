package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.dto.RegisterMemberMasterCounterDto;
import com.microservices.accountservice.entity.RegisterMemberMasterCounter;
import com.microservices.accountservice.exception.AccountAPIException;
import com.microservices.accountservice.repository.RegisterMemberMasterCounterRepository;
import com.microservices.accountservice.service.RegisterMemberMasterCounterService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterMemberMasterCounterImpl implements RegisterMemberMasterCounterService {

    Logger LOGGER = LoggerFactory.getLogger(RegisterMemberMasterCounterImpl.class);

    RegisterMemberMasterCounterRepository registerMemberMasterCounterRepository;

    private ModelMapper modelMapper;

    public RegisterMemberMasterCounterImpl(RegisterMemberMasterCounterRepository registerMemberMasterCounterRepository,
                                           ModelMapper modelMapper) {
        this.registerMemberMasterCounterRepository = registerMemberMasterCounterRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public RegisterMemberMasterCounterDto saveRegisterMemberMasterCounter(RegisterMemberMasterCounterDto registerMemberMasterCounterDto) {

        // add check for username exist in database
        if(registerMemberMasterCounterRepository.existsByRollingNumber(registerMemberMasterCounterDto.getRollingNumber())) {
            throw new AccountAPIException(HttpStatus.BAD_REQUEST, "Rolling Number is already exists!.");
        }

        LOGGER.info("[saveRegisterMemberMasterCounter] RegisterMemberMasterCounterDto: {}", registerMemberMasterCounterDto.getRollingNumber());

        RegisterMemberMasterCounter registerMemberMasterCounter = new RegisterMemberMasterCounter();
        registerMemberMasterCounter.setPrefix("SHKP");
        registerMemberMasterCounter.setRollingNumber(registerMemberMasterCounterDto.getRollingNumber());
        registerMemberMasterCounter.setUpdatedAt(LocalDateTime.now());
        registerMemberMasterCounter.setCreatedAt(LocalDateTime.now());
        RegisterMemberMasterCounter savedRegisterMemberMasterCounter = registerMemberMasterCounterRepository.save(registerMemberMasterCounter);

        // Convert Entity to Dto
        RegisterMemberMasterCounterDto savedRegisterMemberMasterCounterDto = modelMapper.map(savedRegisterMemberMasterCounter, RegisterMemberMasterCounterDto.class);
        return savedRegisterMemberMasterCounterDto;
    }

    @Override
    public RegisterMemberMasterCounterRepository getRegisterMemberMasterCounter() {
        return this.registerMemberMasterCounterRepository;
    }

    @Override
    public RegisterMemberMasterCounterDto findTopByOrderByIdAsc() {
        RegisterMemberMasterCounter registerMemberMasterCounter = registerMemberMasterCounterRepository.findTopByOrderByIdAsc();
        RegisterMemberMasterCounterDto registerMemberMasterCounterDto = new RegisterMemberMasterCounterDto();
        if(registerMemberMasterCounter!=null) {
            registerMemberMasterCounterDto = modelMapper.map(registerMemberMasterCounterRepository.findTopByOrderByIdAsc(), RegisterMemberMasterCounterDto.class);
            return registerMemberMasterCounterDto;
        } else {
            return null;
        }
    }

    @Override
    public RegisterMemberMasterCounterDto findTopByOrderByIdDesc() {
        RegisterMemberMasterCounter registerMemberMasterCounter = registerMemberMasterCounterRepository.findTopByOrderByIdDesc();
        RegisterMemberMasterCounterDto registerMemberMasterCounterDto = new RegisterMemberMasterCounterDto();
        if(registerMemberMasterCounter!=null) {
            registerMemberMasterCounterDto = modelMapper.map(registerMemberMasterCounterRepository.findTopByOrderByIdDesc(), RegisterMemberMasterCounterDto.class);
            return registerMemberMasterCounterDto;
        } else {
            return null;
        }
    }

}
