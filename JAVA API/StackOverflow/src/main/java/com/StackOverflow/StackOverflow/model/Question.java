package com.StackOverflow.StackOverflow.model;

import java.util.List;

public class Question extends Post {
    private List<Answer> Answers;

    public Question(int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount, List<Answer> answers) {
        super(acceptedAnswerId, answerCount, body, closedDate, commentCount, creationDate, favoriteCount, lastActivityDate, lastEditDate, lastEditorDisplayName, lastEditorUserId, ownerUserId, parentId, postTypeId, score, tags, title, viewCount);
        Answers = answers;
    }

    public Question(int id, int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount) {
        super(id,acceptedAnswerId, answerCount, body, closedDate, commentCount, creationDate, favoriteCount, lastActivityDate, lastEditDate, lastEditorDisplayName, lastEditorUserId, ownerUserId, parentId, postTypeId, score, tags, title, viewCount);

    }

    public Question(String body, String tags, String title, int userId) {
        super(body,tags,title,userId);
    }

    public Question(int id, String body, String tags, String title, int userId) {
        super(id,body,tags,title, userId);
    }

    public List<Answer> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<Answer> answers) {
        Answers = answers;
    }
}
