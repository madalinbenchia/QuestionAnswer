package com.StackOverflow.StackOverflow.service;


import com.StackOverflow.StackOverflow.model.Account;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Add account")
    public void AddAccount() {
        Account account = new Account("test","test");
        account.setAccountId(1);

        when(accountRepository.AddAccount(account)).thenReturn(account);

        Account accountResult = accountService.AddAccount(account);

        assertEquals(account.getAccountId(), accountResult.getAccountId());
        assertEquals(account.getUsername(),accountResult.getUsername());

        verify(accountRepository,times(1)).AddAccount(account);
    }
}
