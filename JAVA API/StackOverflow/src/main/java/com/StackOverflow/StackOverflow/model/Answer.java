package com.StackOverflow.StackOverflow.model;

import java.util.List;

public class Answer extends Post {
    private List<Comment> Comments;

    public Answer(int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount, List<Comment> comments) {
        super(acceptedAnswerId, answerCount, body, closedDate, commentCount, creationDate, favoriteCount, lastActivityDate, lastEditDate, lastEditorDisplayName, lastEditorUserId, ownerUserId, parentId, postTypeId, score, tags, title, viewCount);
        Comments = comments;
    }

    public Answer(int id, int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount) {
        super(id,acceptedAnswerId, answerCount, body, closedDate, commentCount, creationDate, favoriteCount, lastActivityDate, lastEditDate, lastEditorDisplayName, lastEditorUserId, ownerUserId, parentId, postTypeId, score, tags, title, viewCount);
    }

    public Answer(String body, String tags, String title) {
        super(body,tags,title);
    }

    public Answer(String body, String tags, String title, int userId) {
        super(body,tags,title,userId);
    }

    public Answer(int id, String body, String tags, String title, int userId) {
        super(id,body,tags,title, userId);
    }
    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }
}
