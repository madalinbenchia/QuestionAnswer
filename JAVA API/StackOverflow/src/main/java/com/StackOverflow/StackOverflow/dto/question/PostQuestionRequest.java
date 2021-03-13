package com.StackOverflow.StackOverflow.dto.question;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostQuestionRequest {
    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "body", required = true, notes = "The text of the question", example = "It is raining outside.", position = 1)
    private String Body;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "tags", required = true, notes = "The tags of the question", example = "Java, Spring", position = 2)
    private String Tags;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "title", required = true, notes = "The title of the question", example = "It is raining?", position = 3)
    private String Title;

    @NotBlank
    @ApiModelProperty(value = "UserId", required = true, notes = "Question userId", example = "1", position = 4)
    private int UserId;

    public PostQuestionRequest(String body, String tags, String title, int userId) {
        Body = body;
        Tags = tags;
        Title = title;
        UserId = userId;
    }

    public String getBody() { return Body; }

    public void setBody(String body) {
        Body = body;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
