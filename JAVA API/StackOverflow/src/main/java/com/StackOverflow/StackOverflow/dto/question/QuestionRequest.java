package com.StackOverflow.StackOverflow.dto.question;

import com.StackOverflow.StackOverflow.dto.PostRequest;

public class QuestionRequest extends PostRequest {

    public QuestionRequest(int acceptedAnswerId, int answerCount, String body, String closedDate, int commentCount, String creationDate, int favoriteCount, String lastActivityDate, String lastEditDate, String lastEditorDisplayName, int lastEditorUserId, int ownerUserId, int parentId, int postTypeId, int score, String tags, String title, int viewCount) {
        super(acceptedAnswerId, answerCount, body, closedDate, commentCount, creationDate, favoriteCount, lastActivityDate, lastEditDate, lastEditorDisplayName, lastEditorUserId, ownerUserId, parentId, postTypeId, score, tags, title, viewCount);

    }


}
