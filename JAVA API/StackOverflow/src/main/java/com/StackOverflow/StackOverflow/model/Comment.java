package com.StackOverflow.StackOverflow.model;

public class Comment {
    private int CommentId;
    private String CreationDate;
    private int PostId;
    private String Text;
    private int UserId;

    public Comment(int postId, String text, int userId) {
        PostId = postId;
        Text = text;
        UserId = userId;
    }

    public Comment(int commentId, int postId, String text, int userId) {
        CommentId = commentId;
        PostId = postId;
        Text = text;
        UserId = userId;
    }

    public Comment(String creationDate, int postId, String text, int userId) {
        CreationDate = creationDate;
        PostId = postId;
        Text = text;
        UserId = userId;
    }
    public Comment(int id, String creationDate, int postId, String text, int userId) {
        CommentId = id;
        CreationDate = creationDate;
        PostId = postId;
        Text = text;
        UserId = userId;
    }
    public Comment(String text, String creationDate, int commentId, int postId,int userId) {
        Text = text;
        CreationDate = creationDate;
        CommentId = commentId;
        PostId = postId;
        UserId = userId;
    }
    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
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
