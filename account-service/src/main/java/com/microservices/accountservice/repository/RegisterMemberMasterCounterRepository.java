package com.microservices.accountservice.repository;

import com.microservices.accountservice.entity.RegisterMemberMasterCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterMemberMasterCounterRepository extends JpaRepository<RegisterMemberMasterCounter, Long> {

    RegisterMemberMasterCounter findByRollingNumber(Integer rollingNumber);
    Boolean existsByRollingNumber (Integer rollingNumber);
    RegisterMemberMasterCounter findTopByOrderByIdAsc();
    RegisterMemberMasterCounter findTopByOrderByIdDesc();

}
