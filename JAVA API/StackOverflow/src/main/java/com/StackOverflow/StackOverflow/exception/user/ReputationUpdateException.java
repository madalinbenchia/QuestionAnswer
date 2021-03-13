package com.StackOverflow.StackOverflow.exception.user;

public class ReputationUpdateException extends RuntimeException{
    public ReputationUpdateException(int id) {super("Can not update reputation for user id " + id);}
}
