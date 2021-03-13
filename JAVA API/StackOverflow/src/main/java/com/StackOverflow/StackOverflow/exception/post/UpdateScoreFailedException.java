package com.StackOverflow.StackOverflow.exception.post;

public class UpdateScoreFailedException extends RuntimeException{
    public UpdateScoreFailedException(String part, int postId) {super("Can not update " + part + " for post id " + postId  + ".");
    }
}
