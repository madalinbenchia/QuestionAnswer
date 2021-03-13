package com.StackOverflow.StackOverflow.exception.user;

public class UpVotesUpdateException extends RuntimeException{
    public UpVotesUpdateException(int id) {super("Can not update UpVotes for User id " + id + ".");}
}
