package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.AccountDto;
import com.microservices.accountservice.entity.Account;

import java.util.List;

public interface AccountService {

    AccountDto saveAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountBySfid(String sfid);

    List<AccountDto> getAllAccounts();

    AccountDto updateAccount(AccountDto accountDto);

    List<Account> findLimitedTo(int limit, String sfid);

    void deleteAllAccount();
}
