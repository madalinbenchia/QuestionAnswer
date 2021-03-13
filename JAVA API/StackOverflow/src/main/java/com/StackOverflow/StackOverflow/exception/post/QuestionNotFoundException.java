package com.StackOverflow.StackOverflow.exception.post;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException() {super("It doesn't exist a question with the specified id.");}
}
