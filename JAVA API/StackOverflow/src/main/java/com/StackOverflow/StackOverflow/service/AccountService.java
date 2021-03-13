package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.model.Account;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account AddAccount(Account account) {

        try {
            return accountRepository.AddAccount(account);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }
    }

    public Account Login(Account account) {
        try {
            return accountRepository.Login(account);
        }catch (SqlException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
