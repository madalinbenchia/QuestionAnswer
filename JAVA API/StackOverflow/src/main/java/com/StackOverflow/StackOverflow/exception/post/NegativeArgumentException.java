package com.StackOverflow.StackOverflow.exception.post;

public class NegativeArgumentException extends RuntimeException {
    public NegativeArgumentException() {super("Argument provided should be greater than 0.");}
}
