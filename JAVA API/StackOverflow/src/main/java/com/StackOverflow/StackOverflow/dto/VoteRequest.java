package com.StackOverflow.StackOverflow.dto;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "postId", required = true, notes = "Answer Id", example = "2", position = 1)
    private int PostId;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "userId", required = true, notes = "User Id", example = "3", position = 2)
    private int UserId;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "VoteTypeId", required = true, notes = "VoteTypeId Id", example = "2 - UpVotes | 3 - DownVotes | 5 - Favorite", position = 3)
    private int VoteTypeId;


    public VoteRequest(int postId, int userId, int voteTypeId) {
        PostId = postId;
        UserId = userId;
        VoteTypeId = voteTypeId;

    }


    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getVoteTypeId() {
        return VoteTypeId;
    }

    public void setVoteTypeId(int voteTypeId) {
        VoteTypeId = voteTypeId;
    }

}
