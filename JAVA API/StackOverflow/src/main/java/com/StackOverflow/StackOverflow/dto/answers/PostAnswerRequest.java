package com.StackOverflow.StackOverflow.dto.answers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

@ApiModel(value = "Answer request", description = "Required details needed to create a new Answer")
public class PostAnswerRequest {
    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "body", required = true, notes = "The text of the answer", example = "I need to know this.", position = 1)
    private String Body;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "tags", required = true, notes = "The tags of the answer", example = "Java, Spring", position = 2)
    private String Tags;

    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(value = "title", required = true, notes = "The title of the answer", example = "Yes, It is raining", position = 3)
    private String Title;

    @NotBlank
    @ApiModelProperty(value = "User Id", required = true, notes = "Answer User Id", example = "1", position = 4)
    private int UserId;

    public PostAnswerRequest(int userId, String body, String tags, String title) {
        UserId = userId;
        Body = body;
        Tags = tags;
        Title = title;
    }


    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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
