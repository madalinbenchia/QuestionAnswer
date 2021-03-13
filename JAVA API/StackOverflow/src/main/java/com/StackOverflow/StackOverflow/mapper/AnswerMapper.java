package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.answers.AnswerRequest;
import com.StackOverflow.StackOverflow.dto.answers.PostAnswerRequest;
import com.StackOverflow.StackOverflow.dto.answers.PutAnswerRequest;
import com.StackOverflow.StackOverflow.model.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public Answer answerRequestToAnswer(AnswerRequest answerRequest) {
        return new Answer(answerRequest.getId(),
                answerRequest.getAcceptedAnswerId(),
                answerRequest.getAnswerCount(),
                answerRequest.getBody(), answerRequest.getClosedDate(),answerRequest.getCommentCount(),
                answerRequest.getCreationDate(),answerRequest.getFavoriteCount(),answerRequest.getLastActivityDate(),
                answerRequest.getLastEditDate(),answerRequest.getLastEditorDisplayName(),answerRequest.getLastEditorUserId(),
                answerRequest.getOwnerUserId(),answerRequest.getParentId(),answerRequest.getPostTypeId(),
                answerRequest.getScore(),answerRequest.getTags(),answerRequest.getTitle(),answerRequest.getViewCount());
    }

    public Answer postAnswerRequestToAnswer(PostAnswerRequest answerRequest) {
        return new Answer(answerRequest.getBody(), answerRequest.getTags(),answerRequest.getTitle(),answerRequest.getUserId());
    }

    public Answer putAnswerRequestToAnswer(PutAnswerRequest answerRequest) {
        return new Answer(answerRequest.getId(), answerRequest.getBody(), answerRequest.getTags(),answerRequest.getTitle(),answerRequest.getUserId());
    }
}
