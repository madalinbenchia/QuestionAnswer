package com.StackOverflow.StackOverflow.dto.question;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PutQuestionRequest {
    @NotNull
    @NotNull
    @ApiModelProperty(value = "id", required = true, notes = "The id of the question", example = "1", position = 1)
    private int Id;

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
    @ApiModelProperty(value = "UserId", required = true, notes = "question userId", example = "1", position = 4)
    private int UserId;


    public PutQuestionRequest(int id, String body, String tags, String title, int userId) {
        Id = id;
        Body = body;
        Tags = tags;
        Title = title;
        UserId = userId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBody() {
        return Body;
    }

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
}
