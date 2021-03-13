package com.StackOverflow.StackOverflow.exception.user;

public class DownVotesUpdateException extends RuntimeException{
    public DownVotesUpdateException(int id) {super("Can not update down votes for user id " + id);}
}
