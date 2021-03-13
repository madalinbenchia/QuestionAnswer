package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.question.PostQuestionRequest;
import com.StackOverflow.StackOverflow.dto.question.PutQuestionRequest;
import com.StackOverflow.StackOverflow.dto.question.QuestionRequest;
import com.StackOverflow.StackOverflow.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question questionRequestToQuestion(QuestionRequest questionRequest) {
        return new Question(questionRequest.getId(),
                            questionRequest.getAcceptedAnswerId(),
                            questionRequest.getAnswerCount(),
                            questionRequest.getBody(), questionRequest.getClosedDate(),questionRequest.getCommentCount(),
                            questionRequest.getCreationDate(),questionRequest.getFavoriteCount(),questionRequest.getLastActivityDate(),
                            questionRequest.getLastEditDate(),questionRequest.getLastEditorDisplayName(),questionRequest.getLastEditorUserId(),
                            questionRequest.getOwnerUserId(),questionRequest.getParentId(),questionRequest.getPostTypeId(),
                            questionRequest.getScore(),questionRequest.getTags(),questionRequest.getTitle(),questionRequest.getViewCount());
    }

    public Question postQuestionRequestToQuestion(PostQuestionRequest questionRequest) {
        return new Question(questionRequest.getBody(),questionRequest.getTags(),questionRequest.getTitle(),questionRequest.getUserId());
    }

    public Question putQuestionRequestToQuestion(PutQuestionRequest questionRequest) {
        return new Question(questionRequest.getId(),questionRequest.getBody(),questionRequest.getTags(),questionRequest.getTitle(), questionRequest.getUserId());
    }
}
