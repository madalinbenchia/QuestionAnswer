package com.StackOverflow.StackOverflow.exception.comment;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(int id) {super("Comment id " + id + " can not be found.");}
}
