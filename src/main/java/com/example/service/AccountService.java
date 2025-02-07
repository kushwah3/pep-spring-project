package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        if (userExists(account) != null) return null;
        return accountRepository.save(account);
    }

    public Account loginUser(Account account) {
        if (userExists(account) == null) return null;
        Account currAccount = userExists(account);
        if (currAccount.getPassword().equals(account.getPassword())) {
            return currAccount;
        }

        return null;
    }

    private Account userExists(Account account) {
        List<Account> accounts = accountRepository.findAll();
        for (Account acc : accounts) {
            if (acc.getUsername().equals(account.getUsername())) {
                return acc;
            }
        }

        return null;
    }
}
