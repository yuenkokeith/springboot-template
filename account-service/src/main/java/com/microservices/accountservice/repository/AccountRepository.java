package com.microservices.accountservice.repository;

import com.microservices.accountservice.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findBySfid(String sfid);

}
