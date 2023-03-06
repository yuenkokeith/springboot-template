package com.microservices.accountservice.service.impl;

import com.microservices.accountservice.repository.AccountRepository;
import com.microservices.accountservice.dto.AccountDto;
import com.microservices.accountservice.entity.Account;
import com.microservices.accountservice.service.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    ModelMapper modelMapper = new ModelMapper();

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> findLimitedTo(int limit, String sfid) {
        return entityManager.createQuery("SELECT sfid FROM Account acc WHERE sfid='"+ sfid + "' ORDER BY acc.Id",
                Account.class).setMaxResults(limit).getResultList();
    }

    public AccountDto saveAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account savedAccount  = accountRepository.save(account);

        AccountDto savedAccountDto = modelMapper.map(savedAccount, AccountDto.class);
        return savedAccountDto;
    }

    public void deleteAllAccount() {
        accountRepository.deleteAll();
    }

    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).get();
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        return accountDto;
    }
    public AccountDto getAccountBySfid(String sfid) {
        Account account = accountRepository.findBySfid(sfid);
        if(account!=null) {
            AccountDto accountDto = modelMapper.map(account, AccountDto.class);
            return accountDto;
        } else {
            return null;
        }
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        if(accounts.size()!=0) {
            List<AccountDto> accountDtos = accounts
                    .stream()
                    .map(Account -> modelMapper.map(Account, AccountDto.class))
                    .collect(Collectors.toList());

            return accountDtos;
        } else {
            return null;
        }
    }

    public AccountDto updateAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account savedAccount  = accountRepository.save(account);
        AccountDto savedAccountDto = modelMapper.map(savedAccount, AccountDto.class);
        return savedAccountDto;
    }

}
