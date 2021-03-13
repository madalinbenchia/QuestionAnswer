package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.exception.user.DownVotesUpdateException;
import com.StackOverflow.StackOverflow.exception.user.ReputationUpdateException;
import com.StackOverflow.StackOverflow.exception.user.UpVotesUpdateException;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.model.Account;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import com.StackOverflow.StackOverflow.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private  final UserRepository userRepository;
    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Transactional
    public User AddUser(User user) {
        Account toBeAddedAcccount = new Account(user.getUsername(),user.getPassword());
        Account addedAccount = accountService.AddAccount(toBeAddedAcccount);
        user.setAccountId(addedAccount.getAccountId());
        user.setPassword("");
        return userRepository.AddUser(user);
    }

    public User GetUser(User user) {
        try {
            Account checkedAccount = accountService.Login(new Account(user.getUsername(), user.getPassword()));
            User checkedUser = userRepository.Login(checkedAccount.getAccountId());
            checkedUser.setPassword("");
            return checkedUser;
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public User GetUserById(int userId) {
        //try {
            User result = userRepository.GetUserById(userId);
            return result;
        /*} catch(Exception ex){
            LOGGER.error(ex.getMessage());
            throw new UserNotFoundException(userId, ex.getMessage());
        }*/
    }

    @Transactional
    public String UpdateUserUpVotes(int userId, int upVotes) {
        try {
            return userRepository.UpdateUserUpVotes(userId,upVotes);
        }
        catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new UpVotesUpdateException(userId);
        }
    }

    @Transactional
    public String UpdateUserDownVotes(int userId, int downVotes) {
        try {
            return userRepository.UpdateUserDownVotes(userId,downVotes);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new DownVotesUpdateException(userId);
        }
    }

    @Transactional
    public String UpdateUserReputation(int userId, int reputation) {
        try {
            return userRepository.UpdateUserReputation(userId, reputation);
        }catch(SqlException ex) {
            LOGGER.error(ex.getMessage());
            throw new ReputationUpdateException(userId);
        }
    }
}
