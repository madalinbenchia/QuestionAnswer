package com.StackOverflow.StackOverflow.model;

public class Vote {
    private int Id;
    private int PostId;
    private int UserId;
    private int VoteTypeId;
    private String CreationDate;

    public Vote(int postId, int userId, int voteTypeId) {
        PostId = postId;
        UserId = userId;
        VoteTypeId = voteTypeId;
    }

    public Vote(int postId, int userId, int voteTypeId, String creationDate) {
        PostId = postId;
        UserId = userId;
        VoteTypeId = voteTypeId;
        CreationDate = creationDate;
    }

    public Vote(int id, int postId, int userId, int voteTypeId, String creationDate) {
        Id = id;
        PostId = postId;
        UserId = userId;
        VoteTypeId = voteTypeId;
        CreationDate = creationDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }
}
