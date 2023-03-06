package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.RegisterMemberMasterCounterDto;
import com.microservices.accountservice.repository.RegisterMemberMasterCounterRepository;

public interface RegisterMemberMasterCounterService {

    RegisterMemberMasterCounterDto saveRegisterMemberMasterCounter(RegisterMemberMasterCounterDto registerMemberMasterCounterDto);
    RegisterMemberMasterCounterRepository getRegisterMemberMasterCounter();
    RegisterMemberMasterCounterDto findTopByOrderByIdAsc();
    RegisterMemberMasterCounterDto findTopByOrderByIdDesc();


}
