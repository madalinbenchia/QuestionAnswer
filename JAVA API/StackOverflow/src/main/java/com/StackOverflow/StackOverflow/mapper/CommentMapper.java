package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.comments.CommentRequest;
import com.StackOverflow.StackOverflow.dto.comments.PostCommentRequest;
import com.StackOverflow.StackOverflow.dto.comments.PutCommentRequest;
import com.StackOverflow.StackOverflow.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment commentRequestToComment(CommentRequest commentRequest) {
        return new Comment(commentRequest.getId(),
                commentRequest.getCreationDate(),
                commentRequest.getPostId(),
                commentRequest.getText(),
                commentRequest.getUserId());
    }

    public Comment postCommentRequestToComment(PostCommentRequest commentRequest) {
        return new Comment(commentRequest.getPostId(),commentRequest.getText(),commentRequest.getUserId());
    }

    public Comment putCommentRequestToComment(PutCommentRequest commentRequest) {
        return new Comment(commentRequest.getCommentId(),commentRequest.getPostId(),commentRequest.getText(),commentRequest.getUserId());
    }
}
