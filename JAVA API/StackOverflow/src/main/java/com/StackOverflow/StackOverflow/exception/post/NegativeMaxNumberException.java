package com.StackOverflow.StackOverflow.exception.post;

public class NegativeMaxNumberException extends RuntimeException {
    public NegativeMaxNumberException() {super("Argument provided should be greater than 0.");}
}
