package com.StackOverflow.StackOverflow.model;

public class Post {
    private int Id;
    private int AcceptedAnswerId;
    private int AnswerCount;
    private String Body;
    private String ClosedDate;
    private int CommentCount;
    private String CreationDate;
    private int FavoriteCount;
    private String LastActivityDate;
    private String LastEditDate;
    private String LastEditorDisplayName;
    private int LastEditorUserId;
    private int OwnerUserId;
    private int ParentId;
    private int PostTypeId;
    private int Score;
    private String Tags;
    private String Title;
    private int ViewCount;

    public Post() {}
    public Post(String body, String tags, String title) {
        Body = body;
        Tags = tags;
        Title = title;
    }

    public Post(String body, String tags, String title,int userId) {
        Body = body;
        Tags = tags;
        Title = title;
        OwnerUserId = userId;
    }

    public Post(int id, String body, String tags, String title, int userId) {
        Id = id;
        Body = body;
        Tags = tags;
        Title = title;
        OwnerUserId = userId;
    }

    public Post(int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount) {
        AcceptedAnswerId = acceptedAnswerId;
        AnswerCount = answerCount;
        Body = body;
        ClosedDate = closedDate;
        CommentCount = commentCount;
        CreationDate = creationDate;
        FavoriteCount = favoriteCount;
        LastActivityDate = lastActivityDate;
        LastEditDate = lastEditDate;
        LastEditorDisplayName = lastEditorDisplayName;
        LastEditorUserId = lastEditorUserId;
        OwnerUserId = ownerUserId;
        ParentId = parentId;
        PostTypeId = postTypeId;
        Score = score;
        Tags = tags;
        Title = title;
        ViewCount = viewCount;
    }

    public Post(int id, int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount) {
        Id = id;
        AcceptedAnswerId = acceptedAnswerId;
        AnswerCount = answerCount;
        Body = body;
        ClosedDate = closedDate;
        CommentCount = commentCount;
        CreationDate = creationDate;
        FavoriteCount = favoriteCount;
        LastActivityDate = lastActivityDate;
        LastEditDate = lastEditDate;
        LastEditorDisplayName = lastEditorDisplayName;
        LastEditorUserId = lastEditorUserId;
        OwnerUserId = ownerUserId;
        ParentId = parentId;
        PostTypeId = postTypeId;
        Score = score;
        Tags = tags;
        Title = title;
        ViewCount = viewCount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAcceptedAnswerId() {
        return AcceptedAnswerId;
    }

    public void setAcceptedAnswerId(int acceptedAnswerId) {
        AcceptedAnswerId = acceptedAnswerId;
    }

    public int getAnswerCount() {
        return AnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        AnswerCount = answerCount;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getClosedDate() {
        return ClosedDate;
    }

    public void setClosedDate(String closedDate) {
        ClosedDate = closedDate;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public int getFavoriteCount() {
        return FavoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        FavoriteCount = favoriteCount;
    }

    public String getLastActivityDate() {
        return LastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        LastActivityDate = lastActivityDate;
    }

    public String getLastEditDate() {
        return LastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        LastEditDate = lastEditDate;
    }

    public String getLastEditorDisplayName() {
        return LastEditorDisplayName;
    }

    public void setLastEditorDisplayName(String lastEditorDisplayName) {
        LastEditorDisplayName = lastEditorDisplayName;
    }

    public int getLastEditorUserId() {
        return LastEditorUserId;
    }

    public void setLastEditorUserId(int lastEditorUserId) {
        LastEditorUserId = lastEditorUserId;
    }

    public int getOwnerUserId() {
        return OwnerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        OwnerUserId = ownerUserId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getPostTypeId() {
        return PostTypeId;
    }

    public void setPostTypeId(int postTypeId) {
        PostTypeId = postTypeId;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
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

    public int getViewCount() {
        return ViewCount;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }
}
