package com.StackOverflow.StackOverflow.exception.post;

public class StringNullOrEmptyException extends RuntimeException{
    public StringNullOrEmptyException(String part) {super(part + " can not be null or empty"); }
}
