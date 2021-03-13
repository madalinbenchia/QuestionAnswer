package com.StackOverflow.StackOverflow.dto.comments;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PutCommentRequest {
    @NotBlank
    @NotNull
    @ApiModelProperty(value = "commentId", required = true, notes = "The Id of the Comment", example = "2", position = 1)
    private int CommentId;

    @NotBlank
    @NotNull
    @ApiModelProperty(value = "answerId", required = true, notes = "The Id of the Answer", example = "2", position = 2)
    private int PostId;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "text", required = true, notes = "The text of the comment", example = "I don't like this answer.", position = 3)
    private String Text;

    @NotBlank
    @NotNull
    @ApiModelProperty(value = "userId", required = true, notes = "The user Id", example = "2", position = 4)
    private int UserId;



    public PutCommentRequest(int commentId, int postId, String text, int userId) {
        CommentId = commentId;
        PostId = postId;
        Text = text;
        UserId = userId;
    }

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
