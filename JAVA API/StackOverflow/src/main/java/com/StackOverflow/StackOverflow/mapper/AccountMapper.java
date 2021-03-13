package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.user.AccountRequest;
import com.StackOverflow.StackOverflow.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account accountRequestToAccount(AccountRequest accountRequest) {
        return new Account(accountRequest.getUsername(), accountRequest.getPassword());
    }
}
