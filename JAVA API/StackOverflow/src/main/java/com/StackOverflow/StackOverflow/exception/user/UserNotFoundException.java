package com.StackOverflow.StackOverflow.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id, String errorMessage) {super("User with id = " + id + " doesn't exists. Error Message: " + errorMessage); }
}
