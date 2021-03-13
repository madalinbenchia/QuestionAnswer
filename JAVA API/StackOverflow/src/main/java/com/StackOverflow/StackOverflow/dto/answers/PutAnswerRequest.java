package com.StackOverflow.StackOverflow.dto.answers;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PutAnswerRequest {
    @NotNull
    @NotNull
    @ApiModelProperty(value = "id", required = true, notes = "The id of the answer", example = "1", position = 1)
    private int Id;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "body", required = true, notes = "The text of the answer", example = "It is raining? I need to know this.", position = 2)
    private String Body;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "tags", required = true, notes = "The tags of the answer", example = "Java, Spring", position = 3)
    private String Tags;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "title", required = true, notes = "The title of the answer", example = "It is raining?", position = 4)
    private String Title;

    @NotBlank
    @ApiModelProperty(value = "User Id", required = true, notes = "Answer Owner", example = "1", position = 5)
    private int UserId;

    public PutAnswerRequest(int id, String body, String tags, String title, int userId) {
        Id = id;
        Body = body;
        Tags = tags;
        Title = title;
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

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
