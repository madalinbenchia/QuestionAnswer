package com.StackOverflow.StackOverflow.dto.comments;

public class CommentRequest {
    private int Id;
    private String CreationDate;
    private int PostId;
    private String Text;
    private int UserId;


    public CommentRequest(int id, String creationDate, int postId, String text, int userId) {
        Id = id;
        CreationDate = creationDate;
        PostId = postId;
        Text = text;
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
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
