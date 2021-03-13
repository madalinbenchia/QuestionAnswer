package com.StackOverflow.StackOverflow.exception.post;

public class EmptyQuestionListException extends RuntimeException{
    public EmptyQuestionListException() {super("Question List is empty. You don't have any question in database.");}
}
