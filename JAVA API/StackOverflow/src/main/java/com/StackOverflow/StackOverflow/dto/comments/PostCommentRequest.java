package com.StackOverflow.StackOverflow.dto.comments;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostCommentRequest {
    @NotBlank
    @NotNull
    @ApiModelProperty(value = "answerId", required = true, notes = "The Id of the Answer", example = "2", position = 1)
    private int PostId;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "text", required = true, notes = "The text of the comment", example = "I don't like this answer.", position = 2)
    private String Text;

    @NotBlank
    @NotNull
    @ApiModelProperty(value = "userId", required = true, notes = "The user id", example = "3", position = 3)
    private int UserId;

    public PostCommentRequest(int postId, String text, int userId) {
        PostId = postId;
        Text = text;
        UserId = userId;
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
