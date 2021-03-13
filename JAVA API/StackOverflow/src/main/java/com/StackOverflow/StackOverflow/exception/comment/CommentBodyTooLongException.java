package com.StackOverflow.StackOverflow.exception.comment;

public class CommentBodyTooLongException extends  RuntimeException{
    public CommentBodyTooLongException() {super("Comment body is too long. Should be less or equal with 1000 characters.");}
}
