package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.user.AccountRequest;
import com.StackOverflow.StackOverflow.dto.user.UserRequest;
import com.StackOverflow.StackOverflow.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userRequestToUser(UserRequest user) {
        return new User(user.getUsername(), user.getPassword(), user.getAboutMe(), user.getAge(), user.getDisplayName(), user.getLocation());
    }

    public User accountRequestToUser(AccountRequest user) {
        return new User(user.getUsername(), user.getPassword());
    }
}
