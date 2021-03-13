package com.StackOverflow.StackOverflow.exception.vote;

public class WrongVoteTypeException extends RuntimeException{
    public WrongVoteTypeException(int voteTypeId){ super("Vote type Id " + voteTypeId + " doesn't exists.");}
}
