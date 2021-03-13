package com.StackOverflow.StackOverflow.exception.post;

public class AnswerNotFoundException extends RuntimeException{
    public AnswerNotFoundException(int Id){ super("Answer with id " + Id + " doesn't exists");}
}
